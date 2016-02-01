package de.therapeutenkiller.dominium.modell;

import java.util.ArrayList;
import java.util.List;

public abstract class Aggregatwurzel<A extends Aggregatwurzel<A, T>, T> extends Entität<T> {

    private final List<Domänenereignis<A>> änderungen = new ArrayList<>();
    private final long version;

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
}

