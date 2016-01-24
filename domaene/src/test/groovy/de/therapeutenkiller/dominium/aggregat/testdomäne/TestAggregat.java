package de.therapeutenkiller.dominium.aggregat.testdomäne;

import de.therapeutenkiller.dominium.aggregat.Aggregatwurzel;

import java.util.UUID;

public final class TestAggregat extends Aggregatwurzel<TestAggregat, UUID> {

    private long zustand; // NOPMD

    protected TestAggregat(final UUID identitätsmerkmal) {
        super(identitätsmerkmal);
    }

    protected TestAggregat(final TestAggregatSchnappschuss schnappschuss) {
        super(schnappschuss);
    }

    @Override
    protected TestAggregat getSelf() {
        return this;
    }

    public void zustandÄndern(final long payload) {
        bewirkt(new ZustandWurdeGeändert(payload));
    }

    public void falls(final ZustandWurdeGeändert zustandWurdeGeändert) {
        this.zustand = zustandWurdeGeändert.getPayload();
    }
}