package com.github.haschi.dominium.modell;

import java.util.List;
import java.util.stream.Collectors;

/**
 *
 * @param <A> Der Typ einer konkreten Ableitung von Aggregatwurzel.
 * @param <I> Der Typ des Identitätsmerkmals der Aggregatwurzel.
 * @param <T> Der Schnittstelle des Ereignis-Ziels der Domänenereignisse des Aggregats
 */
public abstract class Aggregatwurzel<A extends Aggregatwurzel<A, I, T, S>, I, T, S extends Schnappschuss>
        extends Entität<I>
        implements SchnappschussQuelle, EreignisZiel<T>, Ereignisstromziel<T, S> {

    private Version initialversion;
    private EreignisQuelle<T> ereignisQuelle;
    private Änderungsverfolgung<T> änderungsverfolgung;

    // Der einzig erlaubte Konstruktor. Er greift nicht auf abgeleitete Klassen zu.
    protected Aggregatwurzel(final I identitätsmerkmal, final Version version) {
        super(identitätsmerkmal);

        this.änderungsverfolgung = new Änderungsverfolgung<>(version);
        this.ereignisQuelle = new EreignisQuelle<>();

        this.ereignisQuelle.abonnieren(this.änderungsverfolgung);
        this.ereignisQuelle.abonnieren(this);

        this.initialversion = version;
    }

    // Die nachfolgenden zwei Methoden implementieren das Memento Muster.
    // Können diese in eine eigene Schnittstelle ausgelagert werden?
    protected abstract void wiederherstellenAus(final S schnappschuss);

    public final void wiederherstellenAus(final S schnappschuss, final List<Domänenereignis<T>> stream) {

        this.wiederherstellenAus(schnappschuss);
        this.anwenden(stream);

        this.änderungsverfolgung = new Änderungsverfolgung<>(schnappschuss.getVersion().nachfolger(stream.size()));
        this.ereignisQuelle = new EreignisQuelle<>();

        this.ereignisQuelle.abonnieren(this.änderungsverfolgung);
        this.ereignisQuelle.abonnieren(this);

        this.initialversion = this.änderungsverfolgung.getVersion();
    }

    @Override
    public final void wiederherstellenAus(final List<Domänenereignis<T>> stream) {

        this.anwenden(stream);

        this.änderungsverfolgung = new Änderungsverfolgung<>(Version.NEU.nachfolger(stream.size()));
        this.ereignisQuelle = new EreignisQuelle<>();

        this.ereignisQuelle.abonnieren(this.änderungsverfolgung);
        this.ereignisQuelle.abonnieren(this);

        this.initialversion = this.änderungsverfolgung.getVersion();
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
        return this.änderungsverfolgung.alle(ereignis -> ereignis).collect(Collectors.toList());
    }

    protected abstract T getSelf();

    public final Version getVersion() {
        return this.änderungsverfolgung.getVersion();
    }

    public final Version getInitialversion() {
        return this.initialversion;
    }

    public final EreignisQuelle<T> getEreignisQuelle() {
        return this.ereignisQuelle;
    }

    public final Änderungsverfolgung<T> getÄnderungsverfolgung() {
        return this.änderungsverfolgung;
    }

    @Override
    public final void falls(final Domänenereignis<T> ereignis) {
        ereignis.anwendenAuf(this.getSelf());
    }
}

