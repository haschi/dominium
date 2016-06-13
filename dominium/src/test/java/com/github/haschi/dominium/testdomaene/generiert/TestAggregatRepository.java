package com.github.haschi.dominium.testdomaene.generiert;

import com.github.haschi.dominium.modell.Version;

import java.util.List;
import java.util.UUID;

public class TestAggregatRepository {

    private final TestAggregatEventStore storage;

    public TestAggregatRepository(final TestAggregatEventStore storage) {
        super();
        this.storage = storage;
    }

    public TestAggregatProxy getById(final UUID identitätsmerkmal) {
        final TestAggregatProxy proxy = new TestAggregatProxy(identitätsmerkmal, Version.NEU);
        proxy.wiederherstellen(this.storage.getEventsForAggregate(identitätsmerkmal));
        return proxy;
    }

    public void save(final TestAggregatProxy aggregat) {
        final List<TestAggregatEreignis> changes = aggregat.getUncommitedChanges();
        this.storage.saveEvents(aggregat.getIdentitätsmerkmal(), changes, aggregat.getVersion());
    }
}
