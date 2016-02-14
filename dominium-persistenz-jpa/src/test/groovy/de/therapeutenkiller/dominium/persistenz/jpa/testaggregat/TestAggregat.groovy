package de.therapeutenkiller.dominium.persistenz.jpa.testaggregat

import de.therapeutenkiller.dominium.modell.Aggregatwurzel
import de.therapeutenkiller.dominium.modell.Schnappschuss
import groovy.transform.CompileStatic

class TestAggregat extends Aggregatwurzel<TestAggregat, Long> {

    long zustand

    public TestAggregat(final Long identitätsmerkmal) {
        super(identitätsmerkmal)
    }

    public TestAggregat(final TestSchnappschuss schnappschuss) {
        super(schnappschuss)
        this.zustand = schnappschuss.zustand
    }

    @Override
    Schnappschuss<TestAggregat, Long> schnappschussErstellen() {
        return null
    }

    @Override
    protected TestAggregat getSelf() {
        return this
    }

    void falls(ZustandWurdeGeändert zustandWurdeGeändert) {
        this.zustand = zustandWurdeGeändert.payload
    }

    void einenZustandÄndern(long wert) {
        this.bewirkt(new ZustandWurdeGeändert(wert))
    }
}
