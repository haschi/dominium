package com.github.haschi.dominium.modell;

import java.util.List;
import java.util.stream.Collectors;

/**
 *
 * @param <A> Der Typ einer konkreten Ableitung von Aggregatwurzel.
 * @param <I> Der Typ des Identitätsmerkmals der Aggregatwurzel.
 * @param <T> Der Schnittstelle des Ereignis-Ziels der Domänenereignisse des Aggregats
 */
public abstract class Aggregatwurzel<A extends Aggregatwurzel<A, I, T, S>, I, T, S extends Schnappschuss<I>>
        extends Entität<I>
        implements SchnappschussQuelle<A, I>, EreignisZiel<T> {

    private Version initialversion;
    private EreignisQuelle<T> ereignisQuelle;
    private Änderungsverfolgung<T> änderungen;

    // Der einzig erlaubte Konstruktor. Er greift nicht auf abgeleitete Klassen zu.
    protected Aggregatwurzel(final I identitätsmerkmal, final Version version) {
        super(identitätsmerkmal);

        this.änderungen = new Änderungsverfolgung<>(version);
        this.ereignisQuelle = new EreignisQuelle<>();

        this.ereignisQuelle.abonnieren(this.änderungen);
        this.ereignisQuelle.abonnieren(this);

        this.initialversion = version;
    }

    // Die nachfolgenden zwei Methoden implementieren das Memento Muster.
    // Können diese in eine eigene Schnittstelle ausgelagert werden?
    protected abstract void wiederherstellenAus(final S schnappschuss);

    public final void wiederherstellenAus(final S schnappschuss, final List<Domänenereignis<T>> stream) {

        this.wiederherstellenAus(schnappschuss);
        this.anwenden(stream);

        this.änderungen = new Änderungsverfolgung<>(schnappschuss.getVersion().nachfolger(stream.size()));
        this.ereignisQuelle = new EreignisQuelle<>();

        this.ereignisQuelle.abonnieren(this.änderungen);
        this.ereignisQuelle.abonnieren(this);

        this.initialversion = this.änderungen.getVersion();
    }

    public final void wiederherstellenAus(final List<Domänenereignis<T>> stream) {

        this.anwenden(stream);

        this.änderungen = new Änderungsverfolgung<>(Version.NEU.nachfolger(stream.size()));
        this.ereignisQuelle = new EreignisQuelle<>();

        this.ereignisQuelle.abonnieren(this.änderungen);
        this.ereignisQuelle.abonnieren(this);

        this.initialversion = this.änderungen.getVersion();
    }

    protected final void bewirkt(final Domänenereignis<T> ereignis) {
        this.ereignisQuelle.bewirkt(ereignis);
    }

    private void anwenden(final List<Domänenereignis<T>> ereignisse) {
        for (final Domänenereignis<T> ereignis : ereignisse) {
            this.falls(ereignis);
        }
    }

    public abstract S schnappschussErstellen();

    public final List<Domänenereignis<T>> getÄnderungen() {
        return this.änderungen.alle(ereignis -> ereignis).collect(Collectors.toList());
    }

    protected abstract T getSelf();

    public final Version getVersion() {
        return this.änderungen.getVersion();
    }

    public final Version getInitialversion() {
        return this.initialversion;
    }

    @Override
    public final void falls(final Domänenereignis<T> ereignis) {
        ereignis.anwendenAuf(this.getSelf());
    }
}

