package de.therapeutenkiller.haushaltsbuch.persistenz.testdomäne

import de.therapeutenkiller.dominium.aggregat.Domänenereignis
import de.therapeutenkiller.dominium.aggregat.Wertobjekt

class ZustandWurdeGeändert extends Wertobjekt implements Domänenereignis<TestAggregat> {

    private final long payload

    ZustandWurdeGeändert(long payload) {

        this.payload = payload
    }

    @Override
    void anwendenAuf(TestAggregat aggregat) {
        aggregat.falls(this)
    }
}
