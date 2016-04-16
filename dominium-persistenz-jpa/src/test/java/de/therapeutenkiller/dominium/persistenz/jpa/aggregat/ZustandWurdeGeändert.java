package de.therapeutenkiller.dominium.persistenz.jpa.aggregat;

import org.apache.commons.lang3.builder.ToStringBuilder;

import javax.persistence.Entity;

@Entity
public class ZustandWurdeGeändert extends TestAggregatEreignis {

    private long payload;

    private ZustandWurdeGeändert() {
        super();
    }

    public ZustandWurdeGeändert(final long payload) {
        super();
        this.payload = payload;
    }

    public final long getPayload() {
        return this.payload;
    }

    @Override
    public final void anwendenAuf(final TestAggregatEreignisziel ereignisZiel) {
        ereignisZiel.falls(this);
    }

    @Override
    public final String toString() {
        return new ToStringBuilder(this)
                .append("payload", this.payload)
                .toString();
    }
}
