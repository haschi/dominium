package de.therapeutenkiller.dominium.modell;

import java.util.ArrayList;
import java.util.List;

public abstract class Aggregatwurzel<A extends Aggregatwurzel<A, E, I, T>, E extends Domänenereignis<T>, I, T>
        extends Entität<I>
        implements SchnappschussQuelle<A, I> {

    private final List<E> änderungen = new ArrayList<>();
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

    protected final void bewirkt(final E ereignis) {
        this.änderungen.add(ereignis);
        this.anwenden(ereignis);
    }

    public final void anwenden(final E ereignis) {
        this.version = this.version + 1L;
        ereignis.anwendenAuf(this.getSelf());
    }

    public final void anwenden(final List<E> ereignisse) {
        for (final E ereignis : ereignisse) {
            this.anwenden(ereignis);
        }
    }

    public final List<E> getÄnderungen() {
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

