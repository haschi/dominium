package com.github.haschi.dominium.testdomaene.generiert;

import com.github.haschi.dominium.modell.Version;
import com.github.haschi.dominium.persistenz.KonkurrierenderZugriff;

import java.util.List;
import java.util.UUID;

public final class TestAggregatRepository {

    private final TestAggregatEventStore storage;

    public TestAggregatRepository(final TestAggregatEventStore storage) {
        super();
        this.storage = storage;
    }

    public TestAggregatProxy getById(final UUID identitätsmerkmal) {
        final List<TestAggregatEvent> events = this.storage.getEventsForAggregate(identitätsmerkmal);
        final Version version = Version.NEU.nachfolger(events.size());
        final TestAggregatProxy proxy = new TestAggregatProxy(identitätsmerkmal, version);

        proxy.wiederherstellen(events);

        return proxy;
    }

    public void save(final TestAggregatProxy aggregat) throws KonkurrierenderZugriff {
        final List<TestAggregatEvent> changes = aggregat.getUncommitedChanges();
        this.storage.saveEvents(aggregat.getIdentitätsmerkmal(), changes, aggregat.getVersion());
        aggregat.markChangesAsCommitted();
    }
}
