package de.therapeutenkiller.dominium.persistenz.atom.testaggregat;

import org.apache.commons.lang3.builder.ToStringBuilder;

public final class ZustandWurdeGeändert extends TestAggregatEreignis {
    private final long wert;

    private ZustandWurdeGeändert() {
        super();
        this.wert = 0L;
    }

    public ZustandWurdeGeändert(final long wert) {
        super();
        this.wert = wert;
    }

    @Override
    public void anwendenAuf(final TestAggregatEreignisziel ereignisZiel) {
        ereignisZiel.falls(this);
    }

    public long getWert() {
        return this.wert;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append("wert", this.wert)
                .toString();
    }
}
