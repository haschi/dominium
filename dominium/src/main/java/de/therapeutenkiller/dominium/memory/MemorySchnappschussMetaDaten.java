package de.therapeutenkiller.dominium.memory;

import java.time.LocalDateTime;

public final class MemorySchnappschussMetaDaten {

    private final String streamName;
    private final LocalDateTime zeitstempel;

    public MemorySchnappschussMetaDaten(final String streamName, final LocalDateTime now) {

        this.streamName = streamName;
        this.zeitstempel = now;
    }

    public String getStreamName() {
        return this.streamName;
    }

    public LocalDateTime getZeitstempel() {
        return this.zeitstempel;
    }
}
