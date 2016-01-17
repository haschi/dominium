package de.therapeutenkiller.haushaltsbuch.persistenz.testdomäne

import de.therapeutenkiller.dominium.aggregat.Aggregatwurzel

class TestAggregat extends Aggregatwurzel<TestAggregat, UUID> {

    long zustand

    protected TestAggregat(UUID identitätsmerkmal) {
        super(identitätsmerkmal)
    }

    protected TestAggregat(TestAggregatSchnappschuss schnappschuss) {
        super(schnappschuss)
    }

    @Override
    protected TestAggregat getSelf() {
        return this
    }

    public void zustandÄndern(long payload) {
        bewirkt new ZustandWurdeGeändert(payload)
    }

    void falls(ZustandWurdeGeändert zustandWurdeGeändert) {
        this.zustand = zustandWurdeGeändert.payload
    }
}
