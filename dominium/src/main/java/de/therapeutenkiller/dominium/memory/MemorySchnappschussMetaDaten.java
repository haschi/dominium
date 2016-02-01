package de.therapeutenkiller.dominium.memory;

import java.time.ZonedDateTime;

public final class MemorySchnappschussMetaDaten {

    private final String ereignisstrom;
    private final ZonedDateTime zeitstempel;

    public MemorySchnappschussMetaDaten(final String ereignisstrom, final ZonedDateTime zeitstempel) {

        this.ereignisstrom = ereignisstrom;
        this.zeitstempel = zeitstempel;
    }

    public String getEreignisstrom() {
        return this.ereignisstrom;
    }

    public ZonedDateTime getZeitstempel() {
        return this.zeitstempel;
    }
}
