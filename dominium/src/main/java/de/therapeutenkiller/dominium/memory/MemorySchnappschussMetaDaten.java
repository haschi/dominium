package de.therapeutenkiller.dominium.memory;

import de.therapeutenkiller.dominium.modell.Wertobjekt;

import java.time.LocalDateTime;

public final class MemorySchnappschussMetaDaten<I> extends Wertobjekt {

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
