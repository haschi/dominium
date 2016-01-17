package de.therapeutenkiller.haushaltsbuch.persistenz.testdomäne

import de.therapeutenkiller.haushaltsbuch.persistenz.testdomaene.Aggregatwurzel

/**
 * Created by matthias on 17.01.16.
 */
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
