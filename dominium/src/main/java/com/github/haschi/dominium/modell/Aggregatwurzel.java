package com.github.haschi.dominium.modell;

/**
 * @param <I> Der Typ des Identitätsmerkmals der Aggregatwurzel.
 * @param <T> Der Schnittstelle des Ereignis-Ziels der Domänenereignisse des Aggregats
 * @param <S> Der Typ des Schnappschusses, den das Aggregat erzeugt.
 */
public abstract class Aggregatwurzel<I, T, S extends Schnappschuss>
        extends Entität<I>
        implements SchnappschussQuelle, EreignisZiel<T> {


    // Der einzig erlaubte Konstruktor. Er greift nicht auf abgeleitete Klassen zu.
    protected Aggregatwurzel(final I identitätsmerkmal, final Version version) {
        super(identitätsmerkmal);
    }

    // Die nachfolgenden zwei Methoden implementieren das Memento Muster.
    // Können diese in eine eigene Schnittstelle ausgelagert werden?
    public abstract void wiederherstellenAus(final S schnappschuss);

    protected final void bewirkt(final Domänenereignis<T> ereignis) {
    }

    public abstract S schnappschussErstellen();

    protected abstract T getSelf();

    @Override
    public final void falls(final Domänenereignis<T> ereignis) {
        ereignis.anwendenAuf(this.getSelf());
    }
}

