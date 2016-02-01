package de.therapeutenkiller.dominium.modell.testdomäne

import de.therapeutenkiller.dominium.modell.Aggregatwurzel
import de.therapeutenkiller.dominium.modell.Schnappschuss

class TestAggregat extends Aggregatwurzel<TestAggregat, UUID> {

    public long zustand; // NOPMD

    public TestAggregat(final UUID identitätsmerkmal) {
        super(identitätsmerkmal);
    }

    @Override
    TestAggregatSchnappschuss schnappschussErstellen() {

        return TestAggregatSchnappschuss.builder()
            .identitätsmerkmal(this.identitätsmerkmal)
            .version(this.version)
            .payload(this.zustand)
            .build()
    }

    protected TestAggregat(final TestAggregatSchnappschuss schnappschuss) {
        super(schnappschuss);
        this.zustand = schnappschuss.payload
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