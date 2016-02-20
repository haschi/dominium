package de.therapeutenkiller.dominium.memory;

import de.therapeutenkiller.dominium.modell.Domänenereignis;
import de.therapeutenkiller.dominium.persistenz.Ereignisstrom;

public final class MemoryEreignisstrom<I> extends Ereignisstrom<I, MemoryEreignisMetaDaten<I>> {

    public MemoryEreignisstrom(final I streamName) {
        super(streamName);
    }

    public long getVersion() {
        return this.version;
    }

    @Override
    public I getIdentitätsmerkmal() {
        return this.identitätsmerkmal;
    }

    @Override
    protected <A> MemoryDomänenereignisUmschlag<A, I> umschlagErzeugen(final Domänenereignis<A> ereignis) {
        final MemoryEreignisMetaDaten<I> metaDaten = new MemoryEreignisMetaDaten<I>();
        metaDaten.ereignisstrom = this.identitätsmerkmal;
        metaDaten.version = this.version;

        return new MemoryDomänenereignisUmschlag<A, I>(ereignis, metaDaten);
    }
}
