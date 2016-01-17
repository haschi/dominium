package de.therapeutenkiller.haushaltsbuch.persistenz.testdomäne

import de.therapeutenkiller.haushaltsbuch.domaene.support.Wertobjekt
import de.therapeutenkiller.haushaltsbuch.persistenz.testdomaene.Domänenereignis

/**
 * Created by matthias on 17.01.16.
 */
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
