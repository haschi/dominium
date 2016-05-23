package com.github.haschi.dominium.persistenz.jpa.aggregat;

import com.github.haschi.dominium.modell.Version;
import com.github.haschi.dominium.modell.Aggregatwurzel;

import java.util.UUID;

public class TestAggregat extends Aggregatwurzel<TestAggregat, UUID, TestAggregatEreignisziel, TestSchnappschuss>
        implements TestAggregatEreignisziel {

    long zustand;

    public TestAggregat(final UUID identitätsmerkmal, final Version version) {
        super(identitätsmerkmal, version);
    }

    @Override
    public void wiederherstellenAus(final TestSchnappschuss schnappschuss) {
        this.zustand = schnappschuss.getZustand();
    }

    public TestAggregat(final TestSchnappschuss schnappschuss) {
        super(schnappschuss);
        this.zustand = schnappschuss.getZustand();
    }

    @Override
    public final TestSchnappschuss schnappschussErstellen() {
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