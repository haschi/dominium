package de.therapeutenkiller.dominium.modell.testdomäne

import de.therapeutenkiller.dominium.modell.Aggregatwurzel

class TestAggregat extends Aggregatwurzel<TestAggregat, UUID> {

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