package com.github.haschi.dominium.modell;

import java.util.List;

/**
 *
 * @param <A> Der Typ einer konkreten Ableitung von Aggregatwurzel.
 * @param <I> Der Typ des Identitätsmerkmals der Aggregatwurzel.
 * @param <T> Der Schnittstelle des Ereignis-Ziels der Domänenereignisse des Aggregats
 */
public abstract class Aggregatwurzel<A extends Aggregatwurzel<A, I, T, S>, I, T, S extends Schnappschuss>
        extends Entität<I>
        implements SchnappschussQuelle, EreignisZiel<T>, Ereignisstromziel<T, S> {

    private Aggregatverwalter<T> aggregatverwalter = new Aggregatverwalter<T>();

    // Der einzig erlaubte Konstruktor. Er greift nicht auf abgeleitete Klassen zu.
    protected Aggregatwurzel(final I identitätsmerkmal, final Version version) {
        super(identitätsmerkmal);

        this.aggregatverwalter = Aggregatverwalter.aggregatInitialisieren(version, this);
    }

    // Die nachfolgenden zwei Methoden implementieren das Memento Muster.
    // Können diese in eine eigene Schnittstelle ausgelagert werden?
    protected abstract void wiederherstellenAus(final S schnappschuss);

    public final void wiederherstellenAus(final S schnappschuss, final List<Domänenereignis<T>> stream) {

        this.wiederherstellenAus(schnappschuss);
        Aggregatverwalter.anwenden(this, stream);

        this.aggregatverwalter.setÄnderungsverfolgung(new Änderungsverfolgung<>(schnappschuss.getVersion()
            .nachfolger(stream.size())));
        this.aggregatverwalter.setEreignisQuelle(new EreignisQuelle<>());

        this.aggregatverwalter.getEreignisQuelle().abonnieren(this.aggregatverwalter.getÄnderungsverfolgung());
        this.aggregatverwalter.getEreignisQuelle().abonnieren(this);

        this.aggregatverwalter.setInitialversion(this.aggregatverwalter.getÄnderungsverfolgung().getVersion());
    }

    @Override
    public final void wiederherstellenAus(final List<Domänenereignis<T>> stream) {
        this.aggregatverwalter.initialisieren(this, stream);
    }

    protected final void bewirkt(final Domänenereignis<T> ereignis) {
        this.aggregatverwalter.bewirkt(ereignis);
    }

    public abstract S schnappschussErstellen();

    public final List<Domänenereignis<T>> getÄnderungen() {
        return this.aggregatverwalter.getÄnderungen();
    }

    protected abstract T getSelf();

    public final Version getVersion() {
        return this.aggregatverwalter.getVersion();
    }

    public final Version getInitialversion() {
        return this.aggregatverwalter.getInitialversion();
    }

    public final EreignisQuelle<T> getEreignisQuelle() {
        return this.aggregatverwalter.getEreignisQuelle();
    }

    public final Änderungsverfolgung<T> getÄnderungsverfolgung() {
        return this.aggregatverwalter.getÄnderungsverfolgung();
    }

    @Override
    public final void falls(final Domänenereignis<T> ereignis) {
        ereignis.anwendenAuf(this.getSelf());
    }
}

