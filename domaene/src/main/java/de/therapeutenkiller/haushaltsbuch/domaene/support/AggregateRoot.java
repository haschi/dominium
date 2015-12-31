package de.therapeutenkiller.haushaltsbuch.domaene.support;

import java.util.List;

public class AggregateRoot<T, A> extends Entität<T> {

    private final EventManager<A> eventManager = new EventManager<>();

    private int version;

    protected AggregateRoot(final T identität) {
        super(identität);
        this.version = 0;
    }

    public AggregateRoot(final Snapshot<T> snapshot) {
        super(snapshot.getIdentifier());
        this.version = snapshot.getVersion();
    }

    protected final void anwenden(final Domänenereignis<A> ereignis, final A aggrgat) {
        this.eventManager.anwenden(ereignis, aggrgat);
    }

    public final List<Domänenereignis<A>> getÄnderungen() {
        return this.eventManager.getÄnderungen();
    }

    protected final void ereignisHinzufügen(final Domänenereignis<A> ereignis) {
        this.eventManager.ereignisHinzufügen(ereignis);
    }

    protected final void versionErhöhen() {
        this.version = this.version + 1;
    }

    public final void setVersion(final int version) {
        this.version = version;
    }

    public final int getVersion() {
        return this.version;
    }
}
