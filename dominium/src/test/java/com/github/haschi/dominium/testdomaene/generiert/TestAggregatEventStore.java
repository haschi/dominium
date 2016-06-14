package com.github.haschi.dominium.testdomaene.generiert;

import com.github.haschi.dominium.modell.Version;
import com.github.haschi.dominium.persistenz.KonkurrierenderZugriff;
import org.immutables.value.Value;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

public final class TestAggregatEventStore {

    private final Map<UUID, List<TestAggregatEreignisDescriptor>> store = new HashMap<>();

    public void saveEvents(final UUID identitätsmerkmal,
                           final List<TestAggregatEvent> changes,
                           final Version version) throws KonkurrierenderZugriff {

        if (!this.store.containsKey(identitätsmerkmal)) {
            this.store.put(identitätsmerkmal, new ArrayList<>());
        }

        final List<TestAggregatEreignisDescriptor> stream = this.store.get(identitätsmerkmal);

        Version aktuelleVersion = Version.NEU.nachfolger(stream.size());
        if (!aktuelleVersion.equals(version)) {
            throw new KonkurrierenderZugriff(version.alsLong(), aktuelleVersion.alsLong());
        }

        for (final TestAggregatEvent ereignis : changes) {



            stream.add(ImmutableTestAggregatEreignisDescriptor.builder()
                .ereignis(ereignis)
                .version(aktuelleVersion)
                .build());

            aktuelleVersion = aktuelleVersion.nachfolger();
        }
    }

    public List<TestAggregatEvent> getEventsForAggregate(final UUID identitätsmerkmal) {
        return this.store.get(identitätsmerkmal).stream()
            .map(i -> i.ereignis())
            .collect(Collectors.toList());
    }

    @Value.Immutable
    interface TestAggregatEreignisDescriptor {

        TestAggregatEvent ereignis();

        Version version();
    }
}
