package de.therapeutenkiller.haushaltsbuch.domaene.support;

import de.therapeutenkiller.coding.aspekte.DarfNullSein;

import javax.enterprise.context.Dependent;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

// E: Snapshot-Typ, A: Aggregat-Typ

/**
 * Der MemoryEventStore ist ein EventStore, der Ereignisse im
 * Arbeitsspeicher verwaltet. Er wird Hauptsächlich für Tests
 * verwendet.
 * @param <E> Der Typ des Snapshots des Aggregats, dessen Ereignisse gespeichert werden.
 * @param <A> Der Typ des Aggregates, dessen Ereignisse verwaltet werden
 */
@Dependent
public class MemoryEventStore<E, A> implements EreignisLager<E, A> {

    private final Map<String, MemoryEreignisstrom<A>> streams = new ConcurrentHashMap<>();
    private final List<Umschlag<A>> events = new ArrayList<>();
    private final List<SnapshotWrapper<E>> snapshots = new ArrayList<>();

    class MemoryEventWrapper<T> implements Umschlag<T> {

        private final Domänenereignis<T> ereignis;
        private final int version;
        private final String stream;

        public MemoryEventWrapper(final Domänenereignis<T> ereignis, final int version, final String stream) {
            super();

            this.ereignis = ereignis;
            this.version = version;
            this.stream = stream;
        }

        public Domänenereignis<T> getEreignis() {
            return this.ereignis;
        }

        @Override
        public int getVersion() {
            return this.version;
        }

        @Override
        public String getStreamName() {
            return this.stream;
        }
    }

    class MemoryEreignisstrom<T> extends AbstrakterEreignisstrom<T> {

        public MemoryEreignisstrom(final String streamName) {
            super(streamName);
        }

        @Override
        public Umschlag<T> onRegisterEvent(final Domänenereignis<T> ereignis, final int version) {
            return new MemoryEventWrapper<>(ereignis, version, this.name);
        }
    }

    @Override
    public final void neuenEreignisstromErzeugen(
            final String streamName,
            final Collection<Domänenereignis<A>> domainEvents) {
        final MemoryEreignisstrom<A> ereignisstrom = new MemoryEreignisstrom<>(streamName);
        this.streams.put(streamName, ereignisstrom);
        this.ereignisseDemStromHinzufügen(streamName, domainEvents, Optional.empty());
    }

    @Override
    public final void ereignisseDemStromHinzufügen( // NOPMD Datenfluss
                                                    final String streamName,
                                                    final Collection<Domänenereignis<A>> domänenereignisse,
                                                    @DarfNullSein final Optional<Integer> erwarteteVersion)  {

        final AbstrakterEreignisstrom<A> stream = this.streams.get(streamName); // NOPMD

        if (erwarteteVersion.isPresent()) {
            this.checkForConcurrencyError(erwarteteVersion.get(), stream);
        }

        for (final Domänenereignis<A> ereignis : domänenereignisse) {
            final Umschlag<A> wrappedEvent = stream.registerEvent(ereignis);
            this.events.add(wrappedEvent);
        }
    }

    private void checkForConcurrencyError(final int expectedVersion, final AbstrakterEreignisstrom<A> stream) {
        final int lastUpdatedVersion = stream.getVersion();

        if (lastUpdatedVersion != expectedVersion) {
            final String error = String.format("Expected: %d. Found: %d", expectedVersion, lastUpdatedVersion);
            throw new IllegalArgumentException(error);
        }
    }

    @Override
    public final List<Domänenereignis<A>> getStream(
            final String streamName,
            final int vonVersion,
            final int bisVersion) {

        final Comparator<? super Umschlag<A>> byVersion = (left, right) -> Integer.compare(
                left.getVersion(),
                right.getVersion());

        return this.events.stream()
            .filter(event -> this.gehörtZumStream(streamName, event))
            .filter(event -> this.istVersionInnerhalb(vonVersion, bisVersion, event))
            .sorted(byVersion)
            .map(Umschlag::getEreignis)
            .collect(Collectors.toList());
    }

    private boolean gehörtZumStream(final String streamName, final Umschlag<A> event) {
        return event.getStreamName().equals(streamName);
    }

    private boolean istVersionInnerhalb(
            final int fromVersion,
            final int toVersion,
            final Umschlag<A> event) {
        return event.getVersion() >= fromVersion && event.getVersion() <= toVersion;
    }

    @Override
    public final void snapshotHinzufügen(final String streamName, final E snapshot) {
        final SnapshotWrapper<E> wrapper = new SnapshotWrapper<>(streamName, snapshot, LocalDateTime.now());
        this.snapshots.add(wrapper);
    }

    @Override
    @DarfNullSein
    public final E getLatestSnapshot(final String streamName) {

        final Comparator<? super SnapshotWrapper<E>> byDateTimeAbsteigend = (left, right) ->
                left.timestamp.compareTo(right.timestamp);

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
        return this.events.stream()
                .filter(event -> this.gehörtZumStream(streamName, event))
                .filter(event -> event.getVersion() == 1)
                .map(Umschlag::getEreignis)
                .findFirst()
                .get();
    }

    public final void clear() {
        this.events.clear();
        this.snapshots.clear();
        this.streams.clear();
    }
}
