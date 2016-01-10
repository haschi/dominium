package de.therapeutenkiller.haushaltsbuch.domaene.support;

public abstract class AbstrakterEreignisstrom<A> extends Wertobjekt { // NOPMD, TODO Regel ändern.
    protected final String name;
    protected int version;

    public AbstrakterEreignisstrom(final String streamName) {
        super();

        this.name = streamName;
        this.version = 0;
    }

    public final int getVersion() {
        return this.version;
    }

    public final EventWrapperSchnittstelle<A> registerEvent(final Domänenereignis<A> ereignis) {
        this.version = this.version + 1;
        return this.onRegisterEvent(ereignis, this.version);
    }

    public abstract EventWrapperSchnittstelle<A> onRegisterEvent(Domänenereignis<A> ereignis, int version);
}
