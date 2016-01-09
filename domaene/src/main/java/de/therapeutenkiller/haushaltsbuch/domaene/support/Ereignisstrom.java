package de.therapeutenkiller.haushaltsbuch.domaene.support;

import java.io.IOException;

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

    public final <A> EventWrapper<A> registerEvent(final Domänenereignis<A> ereignis)  {
        this.version = this.version + 1;
        final byte[] serialisiertesEreignis;
        try {
            serialisiertesEreignis = EventSerializer.serialize(ereignis);
            return new EventWrapper<>(serialisiertesEreignis, this.version, this.name);
        } catch (final IOException exception) {
            throw new IllegalArgumentException("Ging nicht,.", exception);
        }
    }
}
