package de.therapeutenkiller.haushaltsbuch.domaene.support;

public class EventStream<A> {
    private final String name;
    private int version;

    public final int getVersion() {
        return this.version;
    }

    public EventStream(final String streamName) {
        this.name = streamName;
        this.version = 0;
    }

    public final EventWrapper<A> registerEvent(final Domänenereignis<A> ereignis) {
        this.version = this.version + 1;
        return new EventWrapper<A>(ereignis, this.version, this.name);
    }
}
