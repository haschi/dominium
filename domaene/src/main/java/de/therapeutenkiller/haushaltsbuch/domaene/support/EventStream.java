package de.therapeutenkiller.haushaltsbuch.domaene.support;

public class EventStream<T> {
    private final String name;

    public final int getVersion() {
        return this.version;
    }

    private int version;

    public EventStream(final String streamName) {
        this.name = streamName;
        this.version = 0;
    }

    public final EventWrapper<T> registerEvent(final T ereignis) {
        this.version = this.version + 1;
        return new EventWrapper(ereignis, this.version, this.name);
    }
}
