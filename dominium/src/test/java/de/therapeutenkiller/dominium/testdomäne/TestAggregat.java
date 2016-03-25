package de.therapeutenkiller.dominium.testdomäne;

import de.therapeutenkiller.dominium.modell.Aggregatwurzel;

import java.util.UUID;

public final class TestAggregat
        extends Aggregatwurzel<TestAggregat, TestAggregatEreignis, UUID, TestAggregatEreignisziel>
        implements TestAggregatEreignisziel {

    public long zustand; // NOPMD

    public TestAggregat(final UUID identitätsmerkmal) {
        super(identitätsmerkmal);
    }

    @Override
    public TestAggregatSchnappschuss schnappschussErstellen() {

        return null;
    }

    protected TestAggregat(final TestAggregatSchnappschuss schnappschuss) {
        super(schnappschuss);
        this.zustand = schnappschuss.getPayload();
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
