package de.therapeutenkiller.dominium.memory;

import de.therapeutenkiller.coding.aspekte.DarfNullSein;
import de.therapeutenkiller.dominium.modell.Aggregatwurzel;
import de.therapeutenkiller.dominium.modell.Domänenereignis;
import de.therapeutenkiller.dominium.modell.Schnappschuss;
import de.therapeutenkiller.dominium.persistenz.Ereignislager;
import de.therapeutenkiller.dominium.persistenz.Umschlag;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

/**
 * Der MemoryEventStore ist ein EventStore, der Ereignisse im
 * Arbeitsspeicher verwaltet. Er wird Hauptsächlich für Tests
 * verwendet.
 * @param <A> Der Typ des Aggregates, dessen Ereignisse verwaltet werden
 */
public class MemoryEreignislager<A extends Aggregatwurzel<A, I>, I>
        implements Ereignislager<A, I> {

    private final Map<String, MemoryEreignisstrom<A>> streams = new ConcurrentHashMap<>();
    private final List<Umschlag<Domänenereignis<A>, MemoryEreignisMetaDaten>> events = new ArrayList<>();
    private final List<MemorySchnappschussUmschlag<A, I>> snapshots = new ArrayList<>();

    @Override
    public final void neuenEreignisstromErzeugen(
            final String streamName,
            final Collection<Domänenereignis<A>> domänenereignisse) {
        final MemoryEreignisstrom<A> ereignisstrom = new MemoryEreignisstrom<>(streamName);
        this.streams.put(streamName, ereignisstrom);
        this.ereignisseDemStromHinzufügen(streamName, domänenereignisse, Optional.empty());
    }

    @Override
    public final void ereignisseDemStromHinzufügen( // NOPMD Datenfluss
            final String streamName,
            final Collection<Domänenereignis<A>> domänenereignisse,
            final Optional<Long> erwarteteVersion)  {

        final MemoryEreignisstrom<A> stream = this.streams.get(streamName); // NOPMD

        if (erwarteteVersion.isPresent()) {
            this.aufKonkurrierendenZugriffPrüfen(erwarteteVersion.get(), stream);
        }

        for (final Domänenereignis<A> ereignis : domänenereignisse) {

            final Umschlag<Domänenereignis<A>, MemoryEreignisMetaDaten> registrieren =
                    stream.registrieren(ereignis);// NOPMD LoD

            this.events.add(registrieren);
        }
    }

    private void aufKonkurrierendenZugriffPrüfen(final long expectedVersion, final MemoryEreignisstrom<A> stream) {
        final long lastUpdatedVersion = stream.getVersion();

        if (lastUpdatedVersion != expectedVersion) {
            final String error = String.format("Expected: %d. Found: %d", expectedVersion, lastUpdatedVersion);
            throw new IllegalArgumentException(error);
        }
    }

    @Override
    public final List<Domänenereignis<A>> getEreignisListe(
            final String streamName,
            final long vonVersion,
            final long bisVersion) {

        final Comparator<Umschlag<Domänenereignis<A>, MemoryEreignisMetaDaten>>
                byVersion = (left, right) -> Long.compare(
                    left.getMetaDaten().version,
                    right.getMetaDaten().version);

        return this.events.stream()
            .filter(event -> this.gehörtZumStream(streamName, event))
            .filter(event -> MemoryEreignislager.istVersionInnerhalb(vonVersion, bisVersion, event))
            .sorted(byVersion)
            .map(Umschlag::öffnen)
            .collect(Collectors.toList());
    }

    private boolean gehörtZumStream(
            final String streamName,
            final Umschlag<Domänenereignis<A>, MemoryEreignisMetaDaten> ereignis) {

        return ereignis.getMetaDaten().stream.equals(streamName);
    }

    private static <A> boolean istVersionInnerhalb(
            final long fromVersion,
            final long toVersion,
            final Umschlag<Domänenereignis<A>, MemoryEreignisMetaDaten> event) {
        return event.getMetaDaten().version >= fromVersion && event.getMetaDaten().version <= toVersion;
    }

    @Override
    public final void schnappschussHinzufügen(final String streamName, final Schnappschuss<A, I> snapshot) {

        final MemorySchnappschussUmschlag<A, I> wrapper = new MemorySchnappschussUmschlag<>(
                streamName,
                snapshot,
                LocalDateTime.now());

        this.snapshots.add(wrapper);
    }

    @Override
    @DarfNullSein
    public final Optional<Schnappschuss<A, I>> getNeuesterSchnappschuss(final String streamName) {

        final Comparator<? super MemorySchnappschussUmschlag<A, I>> byDateTimeAbsteigend = (left, right) ->
                left.getMetaDaten().getZeitstempel().compareTo(right.getMetaDaten().getZeitstempel());

        return this.snapshots.stream()
                .filter(wrapper -> wrapper.getMetaDaten().getStreamName().equals(streamName))
                .sorted(byDateTimeAbsteigend)
                .map(MemorySchnappschussUmschlag::öffnen)
                .findFirst();
    }

    public final void clear() {
        this.events.clear();
        this.snapshots.clear();
        this.streams.clear();
    }
}
