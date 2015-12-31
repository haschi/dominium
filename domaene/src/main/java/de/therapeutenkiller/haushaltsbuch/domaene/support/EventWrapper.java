package de.therapeutenkiller.haushaltsbuch.domaene.support;

public class EventWrapper<T> {
    private final String id; // NOPMD Das heißt nun mal so.
    public final Domänenereignis<T> ereignis;
    public final int version;
    public final String name;

    public EventWrapper(final Domänenereignis<T> ereignis, final int version, final String name) {
        this.id = String.format("%s(%d)", name, version);
        this.ereignis = ereignis;
        this.version = version;
        this.name = name;
    }
}
