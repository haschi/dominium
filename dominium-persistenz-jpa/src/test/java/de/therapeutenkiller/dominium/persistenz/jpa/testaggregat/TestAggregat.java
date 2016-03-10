package de.therapeutenkiller.dominium.persistenz.jpa.testaggregat;

import de.therapeutenkiller.dominium.modell.Aggregatwurzel;
import de.therapeutenkiller.dominium.modell.Schnappschuss;

import java.util.UUID;

public class TestAggregat extends Aggregatwurzel<TestAggregat, UUID, TestAggregatEreignisziel>
        implements TestAggregatEreignisziel {

    long zustand;

    public TestAggregat(final UUID identitätsmerkmal) {
        super(identitätsmerkmal);
    }

    public TestAggregat(final TestSchnappschuss schnappschuss) {
        super(schnappschuss);
        this.zustand = schnappschuss.getZustand();
    }

    @Override
    public final Schnappschuss<TestAggregat, UUID> schnappschussErstellen() {
        return null;
    }

    @Override
    protected final TestAggregat getSelf() {
        return this;
    }

    @Override
    public final void falls(final ZustandWurdeGeändert zustandWurdeGeändert) {
        this.zustand = zustandWurdeGeändert.getPayload();
    }

    public final void einenZustandÄndern(final long wert) {
        this.bewirkt(new ZustandWurdeGeändert(wert));
    }
}