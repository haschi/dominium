package de.therapeutenkiller.dominium.memory;

import de.therapeutenkiller.dominium.persistenz.Umschlag;

public final class MemoryDomänenereignisUmschlag<A, I>
        implements Umschlag<A, MemoryEreignisMetaDaten<I>> {

    private final A ereignis;
    private final MemoryEreignisMetaDaten<I> meta;

    public MemoryDomänenereignisUmschlag(final A ereignis, final MemoryEreignisMetaDaten<I> meta) {
        super();

        this.ereignis = ereignis;
        this.meta = meta;
    }

    @Override
    public MemoryEreignisMetaDaten<I> getMetaDaten() {
        return this.meta;
    }

    @Override
    public A öffnen() {
        return this.ereignis;
    }
}
