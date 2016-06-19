package com.github.haschi.dominium.testdomaene;

import com.github.haschi.dominium.infrastructure.EventStream;
import com.github.haschi.dominium.infrastructure.ImmutableEventStream;
import com.github.haschi.dominium.modell.Version;
import com.github.haschi.dominium.infrastructure.KonkurrierenderZugriff;
import org.immutables.value.Value;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class MemoryEventStore<T, I> implements com.github.haschi.dominium.infrastructure.EventStore<T, I> {
    private final Map<I, List<Descriptor<T>>> store = new HashMap<>();

    @Override
    public final void saveEvents(final I identitätsmerkmal,
                                 final Iterable<T> changes,
                                 final Version version) throws KonkurrierenderZugriff {

        if (!this.store.containsKey(identitätsmerkmal)) {
            this.store.put(identitätsmerkmal, new ArrayList<>());
        }

        final List<Descriptor<T>> stream = this.store.get(identitätsmerkmal);

        Version aktuelleVersion = Version.NEU.nachfolger(stream.size());

        if (!aktuelleVersion.equals(version)) {
            throw new KonkurrierenderZugriff(version.alsLong(), aktuelleVersion.alsLong());
        }

        for (final T ereignis : changes) {

            final Descriptor<T> descriptor = ImmutableDescriptor.of(ereignis, version);
            stream.add(descriptor);

            aktuelleVersion = aktuelleVersion.nachfolger();
        }
    }

    @Override
    public final EventStream<T> getEventsForAggregate(final I identitätsmerkmal) {
        final List<T> stream = this.store.get(identitätsmerkmal).stream()
            .map(Descriptor::ereignis)
            .collect(Collectors.toList());

        return ImmutableEventStream.of(stream, Version.NEU.nachfolger(stream.size()));
    }

    @Value.Immutable(builder = false)
    interface Descriptor<T> {

        @Value.Parameter T ereignis();

        @Value.Parameter Version version();
    }
}
