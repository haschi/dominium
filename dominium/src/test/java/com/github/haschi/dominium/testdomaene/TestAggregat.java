package com.github.haschi.dominium.testdomaene;

import com.github.haschi.dominium.modell.Aggregatwurzel;
import com.github.haschi.dominium.modell.EreignisZiel;
import com.github.haschi.dominium.modell.Schnappschuss;
import com.github.haschi.dominium.modell.Version;
import org.immutables.value.Value;
import org.immutables.value.Value.Style.BuilderVisibility;
import org.immutables.value.Value.Style.ImplementationVisibility;

import java.util.UUID;

public final class TestAggregat
        extends Aggregatwurzel<UUID, TestAggregatEreignisZiel, TestAggregat.TestAggregatSchnapp>
        implements TestAggregatEreignisZiel, EreignisZiel<TestAggregatEreignisZiel> {

    private long zustand;

    public TestAggregat(final UUID identitätsmerkmal, final Version version) {
        super(identitätsmerkmal, version);
    }

    @Override
    public void wiederherstellenAus(final TestAggregatSchnapp schnappschuss) {
        schnappschuss.restore(this);
    }

    @Override
    public TestAggregatSchnapp schnappschussErstellen() {
        return TestAggregatSchnapp.from(this);
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

    @Value.Immutable
    @Value.Style(typeBuilder = "*Erbauer", defaultAsDefault = true,
        privateNoargConstructor = true,
        builder = "builder",
        visibility = ImplementationVisibility.PRIVATE,
        builderVisibility = BuilderVisibility.PACKAGE)
    public abstract static class TestAggregatSchnapp implements Schnappschuss {

        private static final long serialVersionUID = -2648479788904679134L;

        protected abstract long payload();

        public static TestAggregatSchnapp from(final TestAggregat aggregat) {
            return new TestAggregatSchnappErbauer()
                .payload(aggregat.zustand)
                .version(aggregat.getAggregatverwalter().getVersion())
                .build();
        }

        public final void restore(final TestAggregat aggregat) {
            aggregat.zustand = this.payload();
        }

        // public static class Builder extends TestAggregatSchnappErbauer {}
    }
}
