package de.therapeutenkiller.dominium.memory;

import de.therapeutenkiller.dominium.modell.Aggregatwurzel;
import de.therapeutenkiller.dominium.modell.Domänenereignis;
import de.therapeutenkiller.dominium.modell.Schnappschuss;
import de.therapeutenkiller.dominium.persistenz.Ereignislager;
import de.therapeutenkiller.dominium.persistenz.KonkurrierenderZugriff;
import de.therapeutenkiller.dominium.persistenz.Uhr;
import de.therapeutenkiller.dominium.persistenz.Umschlag;
import de.therapeutenkiller.dominium.persistenz.Versionsbereich;

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

    private final Map<String, MemoryEreignisstrom> ereignisströme = new ConcurrentHashMap<>();
    private final List<Umschlag<Domänenereignis<A>, MemoryEreignisMetaDaten>> ereignisse = new ArrayList<>();
    private final List<MemorySchnappschussUmschlag<A, I>> schnappschüsse = new ArrayList<>();
    private final Uhr uhr;

    MemoryEreignislager(final Uhr uhr) {
        this.uhr = uhr;
    }

    @Override
    public final void neuenEreignisstromErzeugen(
            final String name,
            final Collection<Domänenereignis<A>> domänenereignisse) throws KonkurrierenderZugriff {

        if (this.ereignisströme.containsKey(name)) {
            throw new IllegalArgumentException();
        }

        final MemoryEreignisstrom ereignisstrom = new MemoryEreignisstrom(name);
        this.ereignisströme.put(name, ereignisstrom);
        this.ereignisseDemStromHinzufügen(name, domänenereignisse, ereignisstrom.getVersion());
    }

    @Override
    public final void ereignisseDemStromHinzufügen( // NOPMD Datenfluss
            final String streamName,
            final Collection<Domänenereignis<A>> domänenereignisse,
            final long erwarteteVersion) throws KonkurrierenderZugriff {

        if (!this.ereignisströme.containsKey(streamName)) {
            throw new IllegalArgumentException();
        }

        final MemoryEreignisstrom ereignisstrom = this.ereignisströme.get(streamName); // NOPMD

        this.aufKonkurrierendenZugriffPrüfen(erwarteteVersion, ereignisstrom);


        for (final Domänenereignis<A> ereignis : domänenereignisse) {

            final Umschlag<Domänenereignis<A>, MemoryEreignisMetaDaten> registrieren =
                    ereignisstrom.registrieren(ereignis);// NOPMD LoD

            this.ereignisse.add(registrieren);
        }
    }

    private void aufKonkurrierendenZugriffPrüfen(
            final long erwarteteVersion,
            final MemoryEreignisstrom ereignisstrom) throws KonkurrierenderZugriff {
        final long aktuelleVersion = ereignisstrom.getVersion();

        if (aktuelleVersion != erwarteteVersion) {
            throw new KonkurrierenderZugriff();
        }
    }

    @Override
    public final List<Domänenereignis<A>> getEreignisListe(
            final String streamName, final Versionsbereich bereich) {

        final Comparator<Umschlag<Domänenereignis<A>, MemoryEreignisMetaDaten>>
                byVersion = (left, right) -> Long.compare(
                    left.getMetaDaten().version,
                    right.getMetaDaten().version);

        return this.ereignisse.stream()
            .filter(event -> this.gehörtZumStream(streamName, event))
            .filter(event -> bereich.liegtInnerhalb(event.getMetaDaten().version))
            .sorted(byVersion)
            .map(Umschlag::öffnen)
            .collect(Collectors.toList());
    }

    private boolean gehörtZumStream(
            final String streamName,
            final Umschlag<Domänenereignis<A>, MemoryEreignisMetaDaten> ereignis) {

        return ereignis.getMetaDaten().ereignisstrom.equals(streamName);
    }

    @Override
    public final void schnappschussHinzufügen(final String streamName, final Schnappschuss<A, I> snapshot) {

        if (!this.ereignisströme.containsKey(streamName)) {
            throw new IllegalArgumentException();
        }

        final MemorySchnappschussMetaDaten meta = new MemorySchnappschussMetaDaten(
                streamName,
                this.uhr.jetzt());

        final MemorySchnappschussUmschlag<A, I> wrapper = new MemorySchnappschussUmschlag<>(
                snapshot, meta);

        this.schnappschüsse.add(wrapper);
    }

    @Override
    public final Optional<Schnappschuss<A, I>> getNeuesterSchnappschuss(final String streamName) {

        final Comparator<? super MemorySchnappschussUmschlag<A, I>> byDateTimeAbsteigend = (left, right) ->
                -1 * left.getMetaDaten().getZeitstempel().compareTo(right.getMetaDaten().getZeitstempel());

        return this.schnappschüsse.stream()
                .filter(wrapper -> wrapper.getMetaDaten().getEreignisstrom().equals(streamName))
                .sorted(byDateTimeAbsteigend)
                .map(MemorySchnappschussUmschlag::öffnen)
                .findFirst();
    }

    public final void clear() {
        this.ereignisse.clear();
        this.schnappschüsse.clear();
        this.ereignisströme.clear();
    }
}
