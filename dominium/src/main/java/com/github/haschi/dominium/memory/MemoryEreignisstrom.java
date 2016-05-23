package com.github.haschi.dominium.memory;

import com.github.haschi.dominium.persistenz.Ereignisstrom;

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
    protected <A> MemoryDomänenereignisUmschlag<A, I> umschlagErzeugen(final A ereignis) {
        final MemoryEreignisMetaDaten<I> metaDaten = new MemoryEreignisMetaDaten<>(
            this.identitätsmerkmal, this.version);

        return new MemoryDomänenereignisUmschlag<>(ereignis, metaDaten);
    }
}
