package de.therapeutenkiller.dominium.modell.testdomäne

import de.therapeutenkiller.dominium.modell.Domänenereignis
import de.therapeutenkiller.dominium.modell.Wertobjekt
import groovy.transform.ToString

@ToString
class ZustandWurdeGeändert extends Wertobjekt implements Domänenereignis<TestAggregat> {

    private final long payload;

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