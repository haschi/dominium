package com.github.haschi.dominium.testdomäne;

import com.github.haschi.dominium.modell.Aggregatwurzel;
import com.github.haschi.dominium.modell.Version;
import com.github.haschi.dominium.testdomäne.TestAggregatSchnappschuss.TestAggregatSchnappschussBuilder;

import java.util.UUID;

public final class TestAggregat
        extends Aggregatwurzel<TestAggregat, UUID, TestAggregatEreignisZiel, TestAggregatSchnappschuss>
        implements TestAggregatEreignisZiel {

    private long zustand;

    public TestAggregat(final UUID identitätsmerkmal, final Version version) {
        super(identitätsmerkmal, version);
    }

    @Override
    public void wiederherstellenAus(final TestAggregatSchnappschuss schnappschuss) {
        this.zustand = schnappschuss.getPayload();
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

    @Override
    protected TestAggregat getSelf() {
        return this;
    }

    public void zustandÄndern(final long payload) {
        this.bewirkt(ZustandWurdeGeändert.erbauer().payload(payload).erzeugen());
    }

    public void falls(final ZustandWurdeGeändertDefinition zustandWurdeGeändert) {
        this.zustand = zustandWurdeGeändert.payload();
    }
}
