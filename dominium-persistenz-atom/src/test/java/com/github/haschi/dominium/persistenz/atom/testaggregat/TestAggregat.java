package com.github.haschi.dominium.persistenz.atom.testaggregat;

import com.github.haschi.dominium.modell.Version;
import com.github.haschi.dominium.modell.Aggregatwurzel;

import java.util.UUID;

public final class TestAggregat
        extends Aggregatwurzel<UUID, TestAggregatEreignisziel, TestAggregatSchnappschuss>
        implements TestAggregatEreignisziel {

    private long wert;

    public TestAggregat(final UUID identitätsmerkmal, final Version version) {
        super(identitätsmerkmal, version);
    }

    @Override
    public void wiederherstellenAus(final TestAggregatSchnappschuss schnappschuss) {
        this.wert = schnappschuss.getWert();
    }

    @Override
    protected TestAggregatEreignisziel getSelf() {
        return this;
    }

    @Override
    public TestAggregatSchnappschuss schnappschussErstellen() {
        return null;
    }

    public void einenZustandÄndern(final long wert) {

        this.bewirkt(new ZustandWurdeGeändert(wert));
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
