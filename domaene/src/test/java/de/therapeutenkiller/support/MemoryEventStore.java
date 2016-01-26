package de.therapeutenkiller.support;

import de.therapeutenkiller.coding.aspekte.DarfNullSein;
import de.therapeutenkiller.dominium.aggregat.Domänenereignis;
import de.therapeutenkiller.dominium.aggregat.Initialereignis;
import de.therapeutenkiller.dominium.lagerung.DomänenereignisUmschlag;
import de.therapeutenkiller.dominium.lagerung.EreignisLager;
import de.therapeutenkiller.dominium.lagerung.Ereignisstrom;
import de.therapeutenkiller.dominium.lagerung.SchnappschussUmschlag;

import javax.enterprise.context.Dependent;
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
 * @param <E> Der Typ des Snapshots des Aggregats, dessen Ereignisse gespeichert werden.
 * @param <A> Der Typ des Aggregates, dessen Ereignisse verwaltet werden
 */
@Dependent
public class MemoryEventStore<E, A> implements EreignisLager<E, A> {

    private final Map<String, MemoryEreignisstrom<A>> streams = new ConcurrentHashMap<>();
    private final List<DomänenereignisUmschlag<A>> events = new ArrayList<>();
    private final List<SchnappschussUmschlag<E>> snapshots = new ArrayList<>();

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

        final Ereignisstrom<A> stream = this.streams.get(streamName); // NOPMD

        if (erwarteteVersion.isPresent()) {
            this.aufKonkurrierendenZugriffPrüfen(erwarteteVersion.get(), stream);
        }

        for (final Domänenereignis<A> ereignis : domänenereignisse) {
            final DomänenereignisUmschlag<A> wrappedEvent = stream.registerEvent(ereignis); // NOPMD LoD
            this.events.add(wrappedEvent);
        }
    }

    private void aufKonkurrierendenZugriffPrüfen(final long expectedVersion, final Ereignisstrom<A> stream) {
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

        final Comparator<? super DomänenereignisUmschlag<A>> byVersion = (left, right) -> Long.compare(
                left.getVersion(),
                right.getVersion());

        return this.events.stream()
            .filter(event -> MemoryEventStore.gehörtZumStream(streamName, event))
            .filter(event -> MemoryEventStore.istVersionInnerhalb(vonVersion, bisVersion, event))
            .sorted(byVersion)
            .map(DomänenereignisUmschlag::getEreignis)
            .collect(Collectors.toList());
    }

    private static <A> boolean gehörtZumStream(final String streamName, final DomänenereignisUmschlag<A> ereignis) {
        return ereignis.getStreamName().equals(streamName);
    }

    private static <A> boolean istVersionInnerhalb(
            final long fromVersion,
            final long toVersion,
            final DomänenereignisUmschlag<A> event) {
        return event.getVersion() >= fromVersion && event.getVersion() <= toVersion;
    }

    @Override
    public final void schnappschussHinzufügen(final String streamName, final E snapshot) {

        final SchnappschussUmschlag<E> wrapper = new SchnappschussUmschlag<>(
                streamName,
                snapshot,
                LocalDateTime.now());

        this.snapshots.add(wrapper);
    }

    @Override
    @DarfNullSein
    public final E getNeuesterSchnappschuss(final String streamName) {

        final Comparator<? super SchnappschussUmschlag<E>> byDateTimeAbsteigend = (left, right) ->
                left.timestamp.compareTo(right.timestamp);

        final Optional<E> snapshot = this.snapshots.stream()
                .filter(wrapper -> wrapper.streamName.equals(streamName))
                .sorted(byDateTimeAbsteigend)
                .map(wrapper -> wrapper.snapshot)
                .findFirst();

        if (snapshot.isPresent()) {
            return snapshot.get();
        } else {
            return null;
        }
    }

    @Override
    public final <T> Initialereignis<A, T> getInitialereignis(final String streamName) {
        return (Initialereignis<A, T>)this.events.stream()
                .filter(event -> MemoryEventStore.gehörtZumStream(streamName, event))
                .filter(event -> event.getVersion() == 1)
                .map(DomänenereignisUmschlag::getEreignis)
                .findFirst()
                .get();
    }

    public final void clear() {
        this.events.clear();
        this.snapshots.clear();
        this.streams.clear();
    }
}
