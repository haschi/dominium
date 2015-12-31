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

// T: Ereignistyp, E: Snapshottyp, I Initialereignis, a: Aggregattyp
@Dependent
public class MemoryEventStore<E, A> implements EventStore<E, A> {

    private Map<String, EventStream<A>> streams = new HashMap<>();
    private List<EventWrapper<A>> events = new ArrayList<>();
    private List<SnapshotWrapper<E>> snapshots = new ArrayList<>();

    @Override
    public final void createNewStream(final String streamName, final Collection<Domänenereignis<A>> domainEvents) {
        final EventStream<A> eventStream = new EventStream<>(streamName);
        this.streams.put(streamName, eventStream);
        this.appendEventsToStream(streamName, domainEvents, Optional.empty());
    }

    @Override
    public final void appendEventsToStream(
            final String streamName,
            final Collection<Domänenereignis<A>> domainEvents,
            @DarfNullSein final Optional<Integer> expectedVersion) {

        final EventStream<A> stream = this.streams.get(streamName);

        if (expectedVersion.isPresent()) {
            this.checkForConcurrencyError(expectedVersion.get(), stream);
        }

        for (final Domänenereignis<A> ereignis : domainEvents) {
            final EventWrapper<A> wrappedEvent = stream.registerEvent(ereignis);
            this.events.add(wrappedEvent);
        }
    }

    private void checkForConcurrencyError(final int expectedVersion, final EventStream stream) {
        final int lastUpdatedVersion = stream.getVersion();

        if (lastUpdatedVersion != expectedVersion) {
            final String error = String.format("Expected: %d. Found: %d", expectedVersion, lastUpdatedVersion);
            throw new IllegalArgumentException(error);
        }
    }

    @Override
    public final List<Domänenereignis<A>> getStream(final String streamName, final int fromVersion, final int toVersion) {

        final Comparator<? super EventWrapper<A>> byVersion = new Comparator<EventWrapper<A>>() {
            @Override
            public int compare(final EventWrapper<A> eventWrapper, final EventWrapper<A> t1) {
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

    private boolean gehörtZumStream(final String streamName, final EventWrapper<A> event) {
        return event.name.equals(streamName);
    }

    private boolean istVersionInnerhalb(final int fromVersion, final int toVersion, final EventWrapper<A> event) {
        return event.version >= fromVersion && event.version <= toVersion;
    }

    @Override
    public final void addSnapshot(final String streamName, final E snapshot) {
        final SnapshotWrapper<E> wrapper = new SnapshotWrapper<>(streamName, snapshot, LocalDateTime.now());
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

    @Override
    public final Domänenereignis<A> getInitialEvent(final String streamName) {
        return (this.events.stream()
                .filter(event -> this.gehörtZumStream(streamName, event))
                .filter(event -> event.version == 1)
                .map(wrapper -> wrapper.ereignis)
                .findFirst()
                .get());
    }
}
