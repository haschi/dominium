package de.therapeutenkiller.dominium.modell.testdomäne

import de.therapeutenkiller.dominium.modell.Aggregatwurzel
import de.therapeutenkiller.dominium.modell.Schnappschuss

class TestAggregat extends Aggregatwurzel<TestAggregat, UUID> {

    public long zustand; // NOPMD

    public TestAggregat(final UUID identitätsmerkmal) {
        super(identitätsmerkmal);
    }

    @Override
    Schnappschuss<TestAggregat, UUID> schnappschussErstellen() {

        TestAggregatSchnappschuss schnappschuss = new TestAggregatSchnappschuss()
        schnappschuss.identitätsmerkmal = this.identitätsmerkmal
        schnappschuss.version = this.version
        schnappschuss.payload = this.zustand;

        return schnappschuss
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