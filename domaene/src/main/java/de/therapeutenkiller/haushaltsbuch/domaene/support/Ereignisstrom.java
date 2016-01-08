package de.therapeutenkiller.haushaltsbuch.domaene.support;

public class Ereignisstrom extends Wertobjekt {
    private final String name;
    private int version;

    public final int getVersion() {
        return this.version;
    }

    public Ereignisstrom(final String streamName) {
        super();
        this.name = streamName;
        this.version = 0;
    }

    public final <A> EventWrapper<A> registerEvent(final Domänenereignis<A> ereignis) {
        this.version = this.version + 1;
        return new EventWrapper<>(ereignis, this.version, this.name);
    }
}
