package com.github.haschi.dominium.testdomaene;

import com.github.haschi.dominium.modell.Aggregatwurzel;
import com.github.haschi.dominium.modell.EreignisZiel;
import com.github.haschi.dominium.modell.Memento;
import com.github.haschi.dominium.modell.Schnappschuss;
import com.github.haschi.dominium.modell.Version;
import com.github.haschi.dominium.testdomaene.TestAggregat.Snapshot;

import java.util.UUID;

public final class TestAggregat
        extends Aggregatwurzel<UUID, TestAggregatEreignisZiel, Snapshot>
        implements TestAggregatEreignisZiel, EreignisZiel<TestAggregatEreignisZiel> {

    private long zustand;

    public TestAggregat(final UUID identitätsmerkmal, final Version version) {
        super(identitätsmerkmal, version);
    }

    @Override
    public void wiederherstellenAus(final Snapshot schnappschuss) {
        schnappschuss.restore(this);
    }

    @Override
    public Snapshot schnappschussErstellen() {
        return Snapshot.from(this);
    }

    @Override
    protected TestAggregat getSelf() {
        return this;
    }

    public void zustandÄndern(final long payload) {
        this.bewirkt(ImmutableZustandWurdeGeändert.of(payload));
    }

    public void falls(final ZustandWurdeGeändert zustandWurdeGeändert) {
        this.zustand = zustandWurdeGeändert.payload();
    }

    @Memento
    public abstract static class Snapshot implements Schnappschuss {

        private static final long serialVersionUID = -2648479788904679134L;

        protected abstract long payload();

        public static Snapshot from(final TestAggregat aggregat) {
            return new SnapshotErbauer()
                .payload(aggregat.zustand)
                .version(aggregat.getAggregatverwalter().getVersion())
                .build();
        }

        public final void restore(final TestAggregat aggregat) {
            aggregat.zustand = this.payload();
        }
    }
}
