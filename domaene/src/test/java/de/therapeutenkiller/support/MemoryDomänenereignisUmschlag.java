package de.therapeutenkiller.support;

import de.therapeutenkiller.dominium.aggregat.Domänenereignis;
import de.therapeutenkiller.dominium.lagerung.DomänenereignisUmschlag;

public final class MemoryDomänenereignisUmschlag<T> implements DomänenereignisUmschlag<T> {

    private final Domänenereignis<T> ereignis;
    private final long version;
    private final String stream;

    public MemoryDomänenereignisUmschlag(final Domänenereignis<T> ereignis, final long version, final String stream) {
        super();

        this.ereignis = ereignis;
        this.version = version;
        this.stream = stream;
    }

    public Domänenereignis<T> getEreignis() {
        return this.ereignis;
    }

    @Override
    public long getVersion() {
        return this.version;
    }

    @Override
    public String getStreamName() {
        return this.stream;
    }
}
