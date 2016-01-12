package de.therapeutenkiller.haushaltsbuch.domaene.support;

import java.time.LocalDateTime;

class SchnappschussUmschlag<E> {
    public final String streamName;
    public final E snapshot;
    public final LocalDateTime timestamp;

    public SchnappschussUmschlag(
            final String streamName,
            final E schnappschuss,
            final LocalDateTime erstellungszeitpunkt) {
        this.streamName = streamName;
        this.snapshot = schnappschuss;
        this.timestamp = erstellungszeitpunkt;
    }
}
