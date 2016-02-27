package de.therapeutenkiller.dominium.modell;

import java.util.ArrayList;
import java.util.List;

public abstract class Aggregatwurzel<A extends Aggregatwurzel<A, I, T>, I, T> extends Entität<I> {

    private final List<Domänenereignis<T>> änderungen = new ArrayList<>();
    private long version;
    private long initialversion;

    protected Aggregatwurzel(final I identitätsmerkmal) {
        super(identitätsmerkmal);
        this.version = 0L;
    }

    protected Aggregatwurzel(final Schnappschuss<A, I, T> schnappschuss) {
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

    public final List<Domänenereignis<T>> getÄnderungen() {
        return this.änderungen;
    }

    public abstract Schnappschuss<A, I, T> schnappschussErstellen();

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

