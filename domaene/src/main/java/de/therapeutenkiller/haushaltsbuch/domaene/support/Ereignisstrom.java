package de.therapeutenkiller.haushaltsbuch.domaene.support;

import java.io.IOException;

public class Ereignisstrom<A> extends AbstrakterEreignisstrom<A> {

    public Ereignisstrom(final String streamName) {
        super(streamName);
    }

    @Override
    public final Umschlag<A> onRegisterEvent(final Domänenereignis<A> ereignis, final int version)  {
        try {
            final byte[] serialisiertesEreignis = EventSerializer.serialize(ereignis);
            return new JpaUmschlag<A>(serialisiertesEreignis, version, this.name);
        } catch (final IOException exception) {
            throw new IllegalArgumentException("Das war nix.", exception);
        }
    }
}
