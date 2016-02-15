package de.therapeutenkiller.dominium.persistenz.jpa.testaggregat;

import de.therapeutenkiller.dominium.modell.Domänenereignis;
import de.therapeutenkiller.dominium.modell.Wertobjekt;

public class ZustandWurdeGeändert extends Wertobjekt implements Domänenereignis<TestAggregat> {

    private long payload;

    public ZustandWurdeGeändert(final long payload) {
        this.payload = payload;
    }

    public final long getPayload() {
        return this.payload;
    }

    @Override
    public final void anwendenAuf(final TestAggregat aggregat) {
        aggregat.falls(this);
    }
}
