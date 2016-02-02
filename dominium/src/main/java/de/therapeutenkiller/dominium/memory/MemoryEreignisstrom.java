package de.therapeutenkiller.dominium.memory;

import de.therapeutenkiller.dominium.modell.Domänenereignis;
import de.therapeutenkiller.dominium.persistenz.Ereignisstrom;

public final class MemoryEreignisstrom extends Ereignisstrom<MemoryEreignisMetaDaten> {

    public MemoryEreignisstrom(final String streamName) {
        super(streamName);
    }

    @Override
    protected <A> MemoryDomänenereignisUmschlag<A> umschlagErzeugen(
            final Domänenereignis<A> ereignis) {
        final MemoryEreignisMetaDaten metaDaten = new MemoryEreignisMetaDaten();
        metaDaten.ereignisstrom = this.name;
        metaDaten.version = this.version;

        return new MemoryDomänenereignisUmschlag<A>(ereignis, metaDaten);
    }
}
