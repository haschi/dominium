package de.therapeutenkiller.haushaltsbuch.domaene.support;

import java.util.ArrayList;
import java.util.List;

public abstract class AggregateRoot<T, E> extends Entität<T> {

    private List<E> änderungen;

    private int version;

    protected AggregateRoot(final T identität) {
        super(identität);
        this.änderungen = new ArrayList<>();
        this.version = 0;
    }

    public AggregateRoot(final Snapshot<T> snapshot) {
        super(snapshot.getIdentifier());
        this.version = snapshot.getVersion();
    }

    protected abstract void anwenden(E ereignis);

    public final List<E> getÄnderungen() {
        return this.änderungen;
    }

    protected final void versionErhöhen() {
        this.version = this.version + 1;
    }

    protected final void ereignisHinzufügen(final E ereignis) {
        this.änderungen.add(ereignis);
    }

    public final void setVersion(final int version) {
        this.version = version;
    }

    public final int getVersion() {
        return this.version;
    }
}
