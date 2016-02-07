package de.therapeutenkiller.dominium.persistenz.jpa.testaggregat

import de.therapeutenkiller.dominium.modell.Domänenereignis
import groovy.transform.Canonical
import groovy.transform.CompileStatic
import groovy.transform.EqualsAndHashCode
import groovy.transform.ToString
import jdk.nashorn.internal.objects.annotations.Getter

@ToString
@EqualsAndHashCode
@CompileStatic
@Canonical
class ZustandWurdeGeändert implements Domänenereignis<TestAggregat> {
    long payload

    @Override
    void anwendenAuf(TestAggregat aggregat) {
        aggregat.falls(this)
    }
}
