package de.therapeutenkiller.dominium.persistenz.jpa.testaggregat;

import de.therapeutenkiller.dominium.modell.Aggregatwurzel;
import de.therapeutenkiller.dominium.modell.Schnappschuss;

public class TestAggregat extends Aggregatwurzel<TestAggregat, Long> {

    long zustand;

    public TestAggregat(final Long identitätsmerkmal) {
        super(identitätsmerkmal);
    }

    public TestAggregat(final TestSchnappschuss schnappschuss) {
        super(schnappschuss);
        this.zustand = schnappschuss.getZustand();
    }

    @Override
    public final Schnappschuss<TestAggregat, Long> schnappschussErstellen() {
        return null;
    }

    @Override
    protected final TestAggregat getSelf() {
        return this;
    }

    public final void falls(final ZustandWurdeGeändert zustandWurdeGeändert) {
        this.zustand = zustandWurdeGeändert.getPayload();
    }

    public final void einenZustandÄndern(final long wert) {
        this.bewirkt(new ZustandWurdeGeändert(wert));
    }
}