package de.therapeutenkiller.haushaltsbuch.persistenz.testdomäne;

import de.therapeutenkiller.dominium.aggregat.Domänenereignis;
import de.therapeutenkiller.dominium.aggregat.Wertobjekt;

public final class ZustandWurdeGeändert extends Wertobjekt implements Domänenereignis<TestAggregat> {

    private final long payload;

    private ZustandWurdeGeändert() {
        super();
        this.payload = 0L;
    }

    public ZustandWurdeGeändert(final long payload) {
        super();
        this.payload = payload;
    }

    @Override
    public void anwendenAuf(final TestAggregat aggregat) {
        aggregat.falls(this);
    }

    public long getPayload() {
        return this.payload;
    }
}
