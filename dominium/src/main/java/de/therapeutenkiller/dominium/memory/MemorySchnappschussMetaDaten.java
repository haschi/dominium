package de.therapeutenkiller.dominium.memory;

import java.time.ZonedDateTime;

public final class MemorySchnappschussMetaDaten {

    private final String streamName;
    private final ZonedDateTime zeitstempel;

    public MemorySchnappschussMetaDaten(final String streamName, final ZonedDateTime now) {

        this.streamName = streamName;
        this.zeitstempel = now;
    }

    public String getStreamName() {
        return this.streamName;
    }

    public ZonedDateTime getZeitstempel() {
        return this.zeitstempel;
    }
}
