package de.therapeutenkiller.haushaltsbuch.domaene.support;

public interface EventWrapperSchnittstelle<A> {
    Domänenereignis<A> getEreignis();

    int getVersion();

    String getStreamName();
}
