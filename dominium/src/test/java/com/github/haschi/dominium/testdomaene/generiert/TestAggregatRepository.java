package com.github.haschi.dominium.testdomaene.generiert;

import com.github.haschi.dominium.infrastructure.EventStream;
import com.github.haschi.dominium.infrastructure.KonkurrierenderZugriff;

import java.util.UUID;

public final class TestAggregatRepository {

    private final TestAggregatEventStore storage;

    public TestAggregatRepository(final TestAggregatEventStore storage) {
        super();
        this.storage = storage;
    }

    public TestAggregatProxy getById(final UUID identitätsmerkmal) {
        final EventStream<TestAggregatEvent> stream = this.storage.getEventsForAggregate(identitätsmerkmal);
        final TestAggregatProxy proxy = new TestAggregatProxy(identitätsmerkmal, stream.version());

        proxy.wiederherstellen(stream.events());

        return proxy;
    }

    public void save(final TestAggregatProxy aggregat) throws KonkurrierenderZugriff {
        final Iterable<TestAggregatEvent> changes = aggregat.getUncommittedChanges();
        this.storage.saveEvents(aggregat.getId(), changes, aggregat.getVersion());
        aggregat.markChangesAsCommitted();
    }
}
