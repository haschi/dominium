package de.therapeutenkiller.haushaltsbuch.persistenz.testdomäne;

import de.therapeutenkiller.dominium.aggregat.Schnappschuss;

import java.util.UUID;

public final class TestAggregatSchnappschuss implements Schnappschuss<TestAggregat, UUID> {

    private long version;
    private long payload;
    private UUID identitätsmerkmal;

    public long getPayload() {
        return this.payload;
    }

    public void setPayload(final long payload) {
        this.payload = payload;
    }

    @Override
    public UUID getIdentitätsmerkmal() {
        return this.identitätsmerkmal;
    }

    @Override
    public long getVersion() {
        return this.version;
    }
}
