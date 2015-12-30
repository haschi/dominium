package de.therapeutenkiller.haushaltsbuch.domaene.support;

/**
 * Created by matthias on 30.12.15.
 */
public class EventWrapper<T> {
    public final String id;
    public final T ereignis;
    public final int version;
    public final String name;

    public EventWrapper(final T ereignis, final int version, final String name) {
        this.id = String.format("%s(%d)", name, version);
        this.ereignis = ereignis;
        this.version = version;
        this.name = name;
    }
}
