package de.therapeutenkiller.haushaltsbuch.persistenz;

import de.therapeutenkiller.support.Ereignisstrom;
import de.therapeutenkiller.support.Domänenereignis;
import de.therapeutenkiller.support.DomänenereignisUmschlag;

public class JpaEreignisstrom<A> extends Ereignisstrom<A> {

    public JpaEreignisstrom(final String streamName) {
        super(streamName);
    }

    @Override
    public final DomänenereignisUmschlag<A> umschlagErzeugen(final Domänenereignis<A> ereignis, final int version)  {
        return new JpaDomänenereignisUmschlag<>(ereignis, version, this.name);
    }
}
