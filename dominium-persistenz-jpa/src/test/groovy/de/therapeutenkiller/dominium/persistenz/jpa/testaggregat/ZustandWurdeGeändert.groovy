package de.therapeutenkiller.dominium.persistenz.jpa.testaggregat

import de.therapeutenkiller.dominium.modell.Domänenereignis
import groovy.transform.EqualsAndHashCode
import groovy.transform.ToString

@ToString
@EqualsAndHashCode
class ZustandWurdeGeändert implements Domänenereignis<TestAggregat> {
    long payload

    @Override
    void anwendenAuf(TestAggregat aggregat) {
        aggregat.falls(this)
    }
}
