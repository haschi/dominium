package de.therapeutenkiller.haushaltsbuch.domaene.support;

import java.time.LocalDateTime;

/**
 * Created by matthias on 30.12.15.
 */
public class SnapshotWrapper<E> {
    public final String streamName;
    public final E snapshot;
    public final LocalDateTime timestamp;

    public SnapshotWrapper(final String streamName, final E snapshot, final LocalDateTime now) {
        this.streamName = streamName;
        this.snapshot = snapshot;
        this.timestamp = now;
    }
}
