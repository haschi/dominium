package com.github.haschi.dominium.testdomaene.generiert;

import com.github.haschi.dominium.modell.Version;
import com.github.haschi.dominium.testdomaene.TestAggregat;

import java.util.UUID;

public final class TestAggregatFactory {

    public TestAggregatEventStore createEventStore() {
        return new TestAggregatEventStore();
    }

    public TestAggregatRepository createRepository(final TestAggregatEventStore eventStore) {
        return new TestAggregatRepository(eventStore);
    }

    public TestAggregat createAggregate(final UUID identifier, final Version version) {
        return new TestAggregatProxy(identifier, version);
    }
}
