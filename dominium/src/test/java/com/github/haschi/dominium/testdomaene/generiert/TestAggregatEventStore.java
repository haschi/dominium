package com.github.haschi.dominium.testdomaene.generiert;

import com.github.haschi.dominium.modell.Version;
import org.immutables.value.Value;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

public class TestAggregatEventStore {

    private Map<UUID, List<TestAggregatEreignisDescriptor>> store = new HashMap<>();

    public void saveEvents(final UUID identitätsmerkmal,
                           final List<TestAggregatEreignis> changes,
                           final Version version) {

        Version nächsteVersion = Version.NEU;

        for(final TestAggregatEreignis ereignis : changes) {
            if (!store.containsKey(identitätsmerkmal)) {
                store.put(identitätsmerkmal, new ArrayList<>());
            }

            List<TestAggregatEreignisDescriptor> stream = store.get(identitätsmerkmal);

            stream.add(ImmutableTestAggregatEreignisDescriptor.builder()
                .ereignis(ereignis)
                .version(nächsteVersion)
                .build());

            nächsteVersion = nächsteVersion.nachfolger();
        }
    }

    public List<TestAggregatEreignis> getEventsForAggregate(final UUID identitätsmerkmal) {
        return store.get(identitätsmerkmal).stream()
            .map(i -> i.ereignis())
            .collect(Collectors.toList());
    }

    @Value.Immutable
    interface TestAggregatEreignisDescriptor {
        TestAggregatEreignis ereignis();
        Version version();
    }
}
