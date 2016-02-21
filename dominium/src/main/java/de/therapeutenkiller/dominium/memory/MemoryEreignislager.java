package de.therapeutenkiller.dominium.memory;

import de.therapeutenkiller.dominium.modell.Aggregatwurzel;
import de.therapeutenkiller.dominium.modell.Domänenereignis;
import de.therapeutenkiller.dominium.modell.Schnappschuss;
import de.therapeutenkiller.dominium.persistenz.Ereignislager;
import de.therapeutenkiller.dominium.persistenz.Ereignisstrom;
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
import java.util.stream.Stream;

/**
 * Das MemoryEreignislager ist ein Ereignislager, der Ereignisse im
 * Arbeitsspeicher verwaltet. Er wird Hauptsächlich für Tests verwendet.
 *
 * @param <A> Der Typ des Aggregates, dessen Ereignisse verwaltet werden
 */
public class MemoryEreignislager<A extends Aggregatwurzel<A, I>, I>
        implements Ereignislager<A, I> {

    private final Map<I, MemoryEreignisstrom<I>> ereignisströme = new ConcurrentHashMap<>();
    private final List<Umschlag<Domänenereignis<A>, MemoryEreignisMetaDaten<I>>> ereignisse = new ArrayList<>();
    private final List<MemorySchnappschussUmschlag<A, I>> schnappschüsse = new ArrayList<>();
    private final Uhr uhr;

    public MemoryEreignislager(final Uhr uhr) {
        this.uhr = uhr;
    }

    @Override
    public final void neuenEreignisstromErzeugen(
            final I identitätsmerkmal,
            final Collection<Domänenereignis<A>> domänenereignisse) {

        if (this.ereignisströme.containsKey(identitätsmerkmal)) {
            throw new IllegalArgumentException();
        }

        final MemoryEreignisstrom<I> ereignisstrom = new MemoryEreignisstrom<>(identitätsmerkmal);
        this.ereignisströme.put(identitätsmerkmal, ereignisstrom);
        this.ereignisseHinzufügen(ereignisstrom, domänenereignisse);
    }

    @Override
    public final void ereignisseDemStromHinzufügen(
            final I identitätsmerkmal,
            final long erwarteteVersion,
            final Collection<Domänenereignis<A>> domänenereignisse)
            throws KonkurrierenderZugriff {

        if (!this.ereignisströme.containsKey(identitätsmerkmal)) {
            throw new IllegalArgumentException();
        }

        final MemoryEreignisstrom<I> ereignisstrom = this.ereignisströme.get(identitätsmerkmal); // NOPMD

        this.aufKonkurrierendenZugriffPrüfen(erwarteteVersion, ereignisstrom);
        this.ereignisseHinzufügen(ereignisstrom, domänenereignisse);
    }

    private void ereignisseHinzufügen(
            final MemoryEreignisstrom<I> ereignisstrom,
            final Collection<Domänenereignis<A>> domänenereignisse) {
        for (final Domänenereignis<A> ereignis : domänenereignisse) {
            this.ereignisse.add(ereignisstrom.registrieren(ereignis));
        }
    }

    private void aufKonkurrierendenZugriffPrüfen(
            final long erwarteteVersion,
            final MemoryEreignisstrom ereignisstrom) throws KonkurrierenderZugriff {
        final long aktuelleVersion = ereignisstrom.getVersion();

        if (aktuelleVersion != erwarteteVersion) {
            throw new KonkurrierenderZugriff(erwarteteVersion, aktuelleVersion);
        }
    }

    @Override
    public final List<Domänenereignis<A>> getEreignisliste(final I identitätsmerkmal, final Versionsbereich bereich) {

        final Comparator<Umschlag<Domänenereignis<A>, MemoryEreignisMetaDaten<I>>>
                byVersion = (left, right) -> Long.compare(
                    left.getMetaDaten().version,
                    right.getMetaDaten().version);

        return this.ereignisse.stream()
            .filter(event -> this.gehörtZumStream(identitätsmerkmal, event))
            .filter(event -> bereich.liegtInnerhalb(event.getMetaDaten().version))
            .sorted(byVersion)
            .map(Umschlag::öffnen)
            .collect(Collectors.toList());
    }

    private boolean gehörtZumStream(
            final I identitätsmerkmal,
            final Umschlag<Domänenereignis<A>, MemoryEreignisMetaDaten<I>> ereignis) {

        return ereignis.getMetaDaten().ereignisstrom.equals(identitätsmerkmal);
    }

    @Override
    public final void schnappschussHinzufügen(final I identitätsmerkmal, final Schnappschuss<A, I> snapshot) {

        if (!this.ereignisströme.containsKey(identitätsmerkmal)) {
            throw new IllegalArgumentException();
        }

        final MemorySchnappschussMetaDaten<I> meta = new MemorySchnappschussMetaDaten<>(
                identitätsmerkmal,
                this.uhr.jetzt());

        final MemorySchnappschussUmschlag<A, I> wrapper = new MemorySchnappschussUmschlag<>(
                snapshot,
                meta);

        this.schnappschüsse.add(wrapper);
    }

    @Override
    public final Optional<Schnappschuss<A, I>> getNeuesterSchnappschuss(final I identitätsmerkmal) {

        final Comparator<? super MemorySchnappschussUmschlag<A, I>> nachZeitstempelAbsteigend = (left, right) ->
                -1 * left.getMetaDaten().getZeitstempel().compareTo(right.getMetaDaten().getZeitstempel());

        return this.schnappschüsse.stream()
                .filter(wrapper -> wrapper.getMetaDaten().getEreignisstrom().equals(identitätsmerkmal))
                .sorted(nachZeitstempelAbsteigend)
                .map(MemorySchnappschussUmschlag::öffnen)
                .findFirst();
    }

    @Override
    public final Stream<I> getEreignisströme() {
        return this.ereignisströme.values().stream().map(Ereignisstrom::getIdentitätsmerkmal);
    }

    public final void clear() {
        this.ereignisse.clear();
        this.schnappschüsse.clear();
        this.ereignisströme.clear();
    }
}
