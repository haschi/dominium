package de.therapeutenkiller.support;

public final class MemoryEreignisstrom<T> extends Ereignisstrom<T> {

    public MemoryEreignisstrom(final String streamName) {
        super(streamName);
    }

    @Override
    public DomänenereignisUmschlag<T> umschlagErzeugen(final Domänenereignis<T> ereignis, final int version) {
        return new MemoryEventWrapper<>(ereignis, version, this.name);
    }
}
