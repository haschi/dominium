package de.therapeutenkiller.dominium.memory;

// import de.therapeutenkiller.coding.aspekte.ValueObject;

import java.time.LocalDateTime;

// @ValueObject
public final class MemorySchnappschussMetaDaten<I>  {

    private final I ereignisstrom;
    private final LocalDateTime zeitstempel;

    public MemorySchnappschussMetaDaten(final I ereignisstrom, final LocalDateTime zeitstempel) {
        super();

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
