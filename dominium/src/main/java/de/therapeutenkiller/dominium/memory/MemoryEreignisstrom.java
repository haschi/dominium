package de.therapeutenkiller.dominium.memory;

import de.therapeutenkiller.dominium.modell.Domänenereignis;
import de.therapeutenkiller.dominium.persistenz.Ereignisstrom;

public final class MemoryEreignisstrom<A> extends Ereignisstrom<A, MemoryEreignisMetaDaten> {

    public MemoryEreignisstrom(final String streamName) {
        super(streamName);
    }

    @Override
    protected MemoryDomänenereignisUmschlag<A> umschlagErzeugen(
            final Domänenereignis<A> ereignis) {

        final MemoryEreignisMetaDaten metaDaten = new MemoryEreignisMetaDaten();
        metaDaten.stream = this.name;
        metaDaten.version = this.version;

        return new MemoryDomänenereignisUmschlag<A>(ereignis, metaDaten);
    }
}
