package de.therapeutenkiller.dominium.modell;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @param <A> Der Typ einer konkreten Ableitung von Aggregatwurzel.
 * @param <E> Der Basistyp der Domänenereignisse, die das Aggregat erzeugt bzw. konsumiert.
 * @param <I> Der Typ des Identitätsmerkmals der Aggregatwurzel.
 * @param <T> Der Schnittstelle des Ereignis-Ziels der Domänenereignisse des Aggregats
 */
public abstract class Aggregatwurzel<A extends Aggregatwurzel<A, E, I, T>, E extends Domänenereignis<T>, I, T>
        extends Entität<I>
        implements SchnappschussQuelle<A, I> {

    private final List<Domänenereignis<T>> änderungen = new ArrayList<>();
    private long version;
    private long initialversion;

    protected Aggregatwurzel(final I identitätsmerkmal) {
        super(identitätsmerkmal);
        this.version = 0L;
    }

    protected Aggregatwurzel(final Schnappschuss<A, I> schnappschuss) {
        super(schnappschuss.getIdentitätsmerkmal());
        this.version = schnappschuss.getVersion();
    }

    protected final void bewirkt(final Domänenereignis<T> ereignis) {
        this.änderungen.add(ereignis);
        this.anwenden(ereignis);
    }

    public final void anwenden(final Domänenereignis<T> ereignis) {
        this.version = this.version + 1L;
        ereignis.anwendenAuf(this.getSelf());
    }

    private void anwenden(final List<Domänenereignis<T>> ereignisse) {
        for (final Domänenereignis<T> ereignis : ereignisse) {
            this.anwenden(ereignis);
        }
    }

    public final void aktualisieren(final List<Domänenereignis<T>> stream) {
        this.anwenden(stream);
        this.setInitialversion(this.getVersion());
    }

    public final List<Domänenereignis<T>> getÄnderungen() {
        return this.änderungen;
    }

    protected abstract T getSelf();

    public final long getVersion() {
        return this.version;
    }

    public final void setInitialversion(final long initialversion) {
        this.initialversion = initialversion;
    }

    public final long getInitialversion() {
        return this.initialversion;
    }
}

