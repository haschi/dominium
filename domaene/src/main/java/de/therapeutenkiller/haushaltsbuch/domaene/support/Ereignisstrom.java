package de.therapeutenkiller.haushaltsbuch.domaene.support;

public class Ereignisstrom<A> extends AbstrakterEreignisstrom<A> {

    public Ereignisstrom(final String streamName) {
        super(streamName);
    }

    @Override
    public final DomänenereignisUmschlag<A> onRegisterEvent(final Domänenereignis<A> ereignis, final int version)  {
        return new JpaUmschlag<>(ereignis, version, this.name);
    }
}
