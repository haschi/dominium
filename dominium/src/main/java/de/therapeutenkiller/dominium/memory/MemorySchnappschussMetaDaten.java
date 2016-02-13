package de.therapeutenkiller.dominium.memory;

import java.time.LocalDateTime;

public final class MemorySchnappschussMetaDaten {

    private final String ereignisstrom;
    private final LocalDateTime zeitstempel;

    public MemorySchnappschussMetaDaten(final String ereignisstrom, final LocalDateTime zeitstempel) {

        this.ereignisstrom = ereignisstrom;
        this.zeitstempel = zeitstempel;
    }

    public String getEreignisstrom() {
        return this.ereignisstrom;
    }

    public LocalDateTime getZeitstempel() {
        return this.zeitstempel;
    }
}
