package de.therapeutenkiller.haushaltsbuch.domaene.support;

import com.google.common.collect.Ordering;
import de.therapeutenkiller.coding.aspekte.DarfNullSein;

import javax.enterprise.context.Dependent;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Dependent
public class MemoryEventStore<T, E> implements EventStore<T, E> {

    private Map<String, EventStream<T>> streams = new HashMap<>();
    private List<EventWrapper<T>> events = new ArrayList<>();
    private List<SnapshotWrapper<E>> snapshots = new ArrayList<>();

    @Override
    public final void createNewStream(final String streamName, final Collection<T> domainEvents) {
        final EventStream eventStream = new EventStream(streamName);
        this.streams.put(streamName, eventStream);
        this.appendEventsToStream(streamName, domainEvents, Optional.empty());
    }

    @Override
    public final void appendEventsToStream(
            final String streamName,
            final Collection<T> domainEvents,
            @DarfNullSein final Optional<Integer> expectedVersion) {

        final EventStream stream = this.streams.get(streamName);

        if (expectedVersion.isPresent()) {
            this.checkForConcurrencyError(expectedVersion.get(), stream);
        }

        for (final T ereignis : domainEvents) {
            this.events.add(stream.registerEvent(ereignis));
        }
    }

    private void checkForConcurrencyError(final int expectedVersion, final EventStream stream) {
        final int lastUpdatedVersion = stream.getVersion();

        if (lastUpdatedVersion != expectedVersion) {
            final String error = String.format("Expected: %i. Found: %d", expectedVersion, lastUpdatedVersion);
            throw new IllegalArgumentException(error);
        }
    }

    @Override
    public final List<T> getStream(final String streamName, final int fromVersion, final int toVersion) {

        final Comparator<? super EventWrapper<T>> byVersion = new Comparator<EventWrapper<T>>() {
            @Override
            public int compare(final EventWrapper<T> eventWrapper, final EventWrapper<T> t1) {
                return Integer.compare(eventWrapper.version, t1.version);
            }
        };

        return this.events.stream()
                .filter(event -> this.gehörtZumStream(streamName, event))
                .filter(event -> this.istVersionInnerhalb(fromVersion, toVersion, event))
                .sorted(byVersion)
                .map(wrapper -> wrapper.ereignis)
                .collect(Collectors.toList());
    }

    private boolean gehörtZumStream(final String streamName, final EventWrapper<T> event) {
        return event.name.equals(streamName);
    }

    private boolean istVersionInnerhalb(final int fromVersion, final int toVersion, final EventWrapper<T> event) {
        return event.version >= fromVersion && event.version <= toVersion;
    }

    @Override
    public final void addSnapshot(final String streamName, final E snapshot) {
        final SnapshotWrapper<E> wrapper = new SnapshotWrapper(streamName, snapshot, LocalDateTime.now());
        this.snapshots.add(wrapper);
    }

    @Override
    @DarfNullSein
    public final E getLatestSnapshot(final String streamName) {

        final Comparator<SnapshotWrapper<E>> byDateTimeAbsteigend = new Ordering<SnapshotWrapper<E>>() {
            @Override
            public int compare(final SnapshotWrapper<E> left, final SnapshotWrapper<E> right) {
                return left.timestamp.compareTo(right.timestamp) * -1;
            }
        };

        final Optional<E> snapshot = this.snapshots.stream()
                .filter(wrapper -> wrapper.streamName.equals(streamName))
                .sorted(byDateTimeAbsteigend)
                .map(wrapper -> wrapper.snapshot)
                .findFirst();

        if (snapshot.isPresent()) {
            return snapshot.get();
        } else {
            return null;
        }
    }
}
