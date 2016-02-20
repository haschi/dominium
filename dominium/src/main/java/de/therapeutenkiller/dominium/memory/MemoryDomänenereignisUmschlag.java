package de.therapeutenkiller.dominium.memory;

import de.therapeutenkiller.dominium.modell.Domänenereignis;
import de.therapeutenkiller.dominium.persistenz.Umschlag;

public final class MemoryDomänenereignisUmschlag<A, I>
        implements Umschlag<Domänenereignis<A>, MemoryEreignisMetaDaten<I>> {

    private final Domänenereignis<A> ereignis;
    private final MemoryEreignisMetaDaten<I> meta;

    public MemoryDomänenereignisUmschlag(final Domänenereignis<A> ereignis, final MemoryEreignisMetaDaten<I> meta) {

        this.ereignis = ereignis;
        this.meta = meta;
    }

    @Override
    public MemoryEreignisMetaDaten<I> getMetaDaten() {
        return this.meta;
    }

    @Override
    public Domänenereignis<A> öffnen() {
        return this.ereignis;
    }
}
