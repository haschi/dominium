package de.therapeutenkiller.dominium.memory;

import de.therapeutenkiller.dominium.modell.Domänenereignis;
import de.therapeutenkiller.dominium.persistenz.Umschlag;

public final class MemoryDomänenereignisUmschlag<A> implements Umschlag<Domänenereignis<A>, MemoryEreignisMetaDaten> {

    private final Domänenereignis<A> ereignis;
    private final MemoryEreignisMetaDaten meta;

    public MemoryDomänenereignisUmschlag(final Domänenereignis<A> ereignis, final MemoryEreignisMetaDaten meta) {

        this.ereignis = ereignis;
        this.meta = meta;
    }

    @Override
    public MemoryEreignisMetaDaten getMetaDaten() {
        return this.meta;
    }

    @Override
    public Domänenereignis<A> öffnen() {
        return this.ereignis;
    }
}
