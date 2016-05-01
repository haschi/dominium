package de.therapeutenkiller.dominium.memory;

public class MemoryEreignisMetaDaten<I> {
    public final I ereignisstrom;
    public final long version;

    public MemoryEreignisMetaDaten(final I ereignisstrom, final long version) {
        super();

        this.ereignisstrom = ereignisstrom;
        this.version = version;
    }
}
