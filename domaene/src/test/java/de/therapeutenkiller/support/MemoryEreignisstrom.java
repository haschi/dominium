package de.therapeutenkiller.support;

import de.therapeutenkiller.dominium.aggregat.Domänenereignis;
import de.therapeutenkiller.dominium.lagerung.DomänenereignisUmschlag;
import de.therapeutenkiller.dominium.lagerung.Ereignisstrom;

public final class MemoryEreignisstrom<T> extends Ereignisstrom<T> {

    public MemoryEreignisstrom(final String streamName) {
        super(streamName);
    }

    @Override
    public DomänenereignisUmschlag<T> umschlagErzeugen(final Domänenereignis<T> ereignis, final int version) {
        return new MemoryDomänenereignisUmschlag<>(ereignis, version, this.name);
    }
}
