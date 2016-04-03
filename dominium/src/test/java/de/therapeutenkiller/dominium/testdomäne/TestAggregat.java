package de.therapeutenkiller.dominium.testdomäne;

import de.therapeutenkiller.dominium.modell.Aggregatwurzel;
import de.therapeutenkiller.dominium.testdomäne.TestAggregatSchnappschuss.TestAggregatSchnappschussBuilder;

import java.util.UUID;

public final class TestAggregat
        extends Aggregatwurzel<TestAggregat, TestAggregatEreignis, UUID, TestAggregatEreignisziel>
        implements TestAggregatEreignisziel {

    private long zustand;

    public TestAggregat(final UUID identitätsmerkmal) {
        super(identitätsmerkmal);
    }

    @Override
    public TestAggregatSchnappschuss schnappschussErstellen() {
        final TestAggregatSchnappschussBuilder builder = TestAggregatSchnappschuss.builder();

        return this.schnappschussErstellen(builder).build();
    }

    private TestAggregatSchnappschussBuilder schnappschussErstellen(final TestAggregatSchnappschussBuilder builder) {
        return builder.aggregat(this)
            .payload(this.zustand);
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
        this.bewirkt(new ZustandWurdeGeändert(payload));
    }

    public void falls(final ZustandWurdeGeändert zustandWurdeGeändert) {
        this.zustand = zustandWurdeGeändert.getPayload();
    }
}
