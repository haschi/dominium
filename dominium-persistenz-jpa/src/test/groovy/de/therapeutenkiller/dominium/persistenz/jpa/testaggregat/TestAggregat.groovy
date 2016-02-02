package de.therapeutenkiller.dominium.persistenz.jpa.testaggregat

import de.therapeutenkiller.dominium.modell.Aggregatwurzel
import de.therapeutenkiller.dominium.modell.Schnappschuss

class TestAggregat extends Aggregatwurzel<TestAggregat, Long> {

    long zustand

    protected TestAggregat(final Long identitätsmerkmal) {
        super(identitätsmerkmal)
    }

    @Override
    Schnappschuss<TestAggregat, Long> schnappschussErstellen() {
        return null
    }

    @Override
    protected TestAggregat getSelf() {
        return null
    }

    void falls(ZustandWurdeGeändert zustandWurdeGeändert) {
        this.zustand = zustandWurdeGeändert.payload
    }
}
