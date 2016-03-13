package de.therapeutenkiller.dominium.persistenz.atom.testaggregat;

import de.therapeutenkiller.dominium.modell.Aggregatwurzel;
import de.therapeutenkiller.dominium.modell.Schnappschuss;

import java.util.UUID;

public final class TestAggregat extends Aggregatwurzel<TestAggregat, UUID, TestAggregatEreignisziel>
        implements TestAggregatEreignisziel {

    private long wert;

    public TestAggregat(final UUID identitätsmerkmal) {
        super(identitätsmerkmal);
    }

    @Override
    protected TestAggregatEreignisziel getSelf() {
        return this;
    }

    @Override
    public Schnappschuss<TestAggregat, UUID> schnappschussErstellen() {
        return null;
    }

    public void einenZustandÄndern(final long wert) {

        bewirkt(new ZustandWurdeGeändert(wert));
    }

    @Override
    public void falls(final ZustandWurdeGeändert zustandWurdeGeändert) {
        this.wert = zustandWurdeGeändert.getWert();
    }

    public void ereignisseErzeugen(final long anzahl) {
        for (long i = 0; i < anzahl; i++) {
            this.einenZustandÄndern(i);
        }
    }

    public long getWert() {
        return this.wert;
    }
}
