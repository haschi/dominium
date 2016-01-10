package de.therapeutenkiller.haushaltsbuch.persistenz;

import de.therapeutenkiller.haushaltsbuch.domaene.support.AbstrakterEreignisstrom;
import de.therapeutenkiller.haushaltsbuch.domaene.support.Domänenereignis;
import de.therapeutenkiller.haushaltsbuch.domaene.support.DomänenereignisUmschlag;

public class JpaEreignisstrom<A> extends AbstrakterEreignisstrom<A> {

    public JpaEreignisstrom(final String streamName) {
        super(streamName);
    }

    @Override
    public final DomänenereignisUmschlag<A> onRegisterEvent(final Domänenereignis<A> ereignis, final int version)  {
        return new JpaDomänenereignisUmschlag<>(ereignis, version, this.name);
    }
}
