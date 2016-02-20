package de.therapeutenkiller.dominium.modell;

import java.util.ArrayList;
import java.util.List;

public abstract class Aggregatwurzel<A extends Aggregatwurzel<A, T>, T> extends Entität<T> {

    private final List<Domänenereignis<A>> änderungen = new ArrayList<>();
    private long version;

    private long initialversion;

    protected Aggregatwurzel(final T identitätsmerkmal) {
        super(identitätsmerkmal);
        this.version = 0L;
    }

    protected Aggregatwurzel(final Schnappschuss<A, T> schnappschuss) {
        super(schnappschuss.getIdentitätsmerkmal());
        this.version = schnappschuss.getVersion();
    }

    protected final void bewirkt(final Domänenereignis<A> ereignis) {
        this.änderungen.add(ereignis);
        this.anwenden(ereignis);
    }

    public final void anwenden(final Domänenereignis<A> ereignis) {
        this.version = this.version + 1L;
        ereignis.anwendenAuf(this.getSelf());
    }

    public final List<Domänenereignis<A>> getÄnderungen() {
        return this.änderungen;
    }

    public abstract Schnappschuss<A, T> schnappschussErstellen();

    protected abstract A getSelf();

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

