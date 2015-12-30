package de.therapeutenkiller.haushaltsbuch.domaene.support;

import de.therapeutenkiller.coding.aspekte.DarfNullSein;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

/**
 * Created by matthias on 30.12.15.
 */

// T: Ereignistyp, E: Snapshottyp
public interface EventStore<T, E> {

    void createNewStream(String streamName, Collection<T> domainEvents);

    void appendEventsToStream(String streamName, Collection<T> domainEvents, Optional<Integer> expectedVersion);

    List<T> getStream(String streamName, int fromVersion, int toVersion);

    void addSnapshot(String streamName, E snapshot);

    @DarfNullSein
    E getLatestSnapshot(String streamName);
}
