package de.therapeutenkiller.dominium.testdomäne;

import de.therapeutenkiller.dominium.modell.Schnappschuss;

import java.util.UUID;

public final class TestAggregatSchnappschuss implements Schnappschuss<TestAggregat, UUID> {

    long version;
    long payload;
    UUID identitätsmerkmal;

    @Override
    public UUID getIdentitätsmerkmal() {
        return this.identitätsmerkmal;
    }

    @Override
    public long getVersion() {
        return this.version;
    }

    public long getPayload() {
        return this.payload;
    }

    @Override
    public TestAggregat wiederherstellen() {
        return new TestAggregat(this);
    }
}
