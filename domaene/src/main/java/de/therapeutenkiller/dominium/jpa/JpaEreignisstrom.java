package de.therapeutenkiller.dominium.jpa;

import de.therapeutenkiller.dominium.lagerung.Ereignisstrom;
import de.therapeutenkiller.dominium.aggregat.Domänenereignis;
import de.therapeutenkiller.dominium.lagerung.DomänenereignisUmschlag;

public class JpaEreignisstrom<A> extends Ereignisstrom<A> {

    public JpaEreignisstrom(final String streamName) {
        super(streamName);
    }

    @Override
    public final DomänenereignisUmschlag<A> umschlagErzeugen(final Domänenereignis<A> ereignis, final int version)  {
        return new JpaDomänenereignisUmschlag<>(ereignis, version, this.name);
    }
}
