package de.therapeutenkiller.dominium.memory;

import java.time.LocalDateTime;

public final class MemorySchnappschussMetaDaten<I> {

    private final I ereignisstrom;
    private final LocalDateTime zeitstempel;

    public MemorySchnappschussMetaDaten(final I ereignisstrom, final LocalDateTime zeitstempel) {

        this.ereignisstrom = ereignisstrom;
        this.zeitstempel = zeitstempel;
    }

    public I getEreignisstrom() {
        return this.ereignisstrom;
    }

    public LocalDateTime getZeitstempel() {
        return this.zeitstempel;
    }
}
