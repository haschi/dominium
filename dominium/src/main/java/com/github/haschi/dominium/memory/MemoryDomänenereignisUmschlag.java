package com.github.haschi.dominium.memory;

// import de.therapeutenkiller.coding.aspekte.ValueObject;
import com.github.haschi.dominium.persistenz.Umschlag;

// @ValueObject
public final class MemoryDomänenereignisUmschlag<A, I>
        implements Umschlag<A, MemoryEreignisMetaDaten<I>> {

    private final A ereignis;
    private final MemoryEreignisMetaDaten<I> metaDaten;

    public MemoryDomänenereignisUmschlag(final A ereignis, final MemoryEreignisMetaDaten<I> meta) {
        super();

        this.ereignis = ereignis;
        this.metaDaten = meta;
    }

    @Override
    public MemoryEreignisMetaDaten<I> getMetaDaten() {
        return this.metaDaten;
    }

    @Override
    public A öffnen() {
        return this.ereignis;
    }
}
