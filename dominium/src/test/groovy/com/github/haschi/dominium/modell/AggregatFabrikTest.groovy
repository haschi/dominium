package com.github.haschi.dominium.modell

import com.github.haschi.dominium.testdomaene.TestAggregat
import com.github.haschi.dominium.testdomaene.TestAggregatFabrik
import com.github.haschi.dominium.testdomaene.Testdaten
import com.github.haschi.dominium.testdomaene.ZustandWurdeGeändert
import spock.lang.Specification

class AggregatFabrikTest extends Specification {

    UUID identitätsmerkmal = UUID.randomUUID()

    def "Eine AggregatFabrik kann ein neues Aggregat erzeugen"() {
        given:
        TestAggregatFabrik fabrik = new TestAggregatFabrik();

        when:
        TestAggregat aggregat = fabrik.erzeugen(identitätsmerkmal)

        then:
        aggregat.schnappschussErstellen() == Testdaten.schnappschuss(0, 0L)
    }

    def "Eine AggregatFabrik kann aus einem Ereignisstrom ein Aggregat erzeugen"() {
        given:
        TestAggregatFabrik fabrik = new TestAggregatFabrik();

        when:
        TestAggregat aggregat = fabrik.erzeugen(identitätsmerkmal, [ZustandWurdeGeändert.of(42L)])

        then:
        aggregat.schnappschussErstellen() == Testdaten.schnappschuss(1, 42L)
    }

    def "Eine AggregatFabrik kann aus einem Schnappschuss und einem Ereignisstrom ein Aggregat erzeugen"() {
        given:
        TestAggregatFabrik fabrik = new TestAggregatFabrik();
        TestAggregat.Snapshot schnappschuss = Testdaten.schnappschuss(5, 42L);

        when:
        TestAggregat aggregat = fabrik.erzeugen(identitätsmerkmal, schnappschuss, [ZustandWurdeGeändert.of(4711L)])

        then:
        aggregat.schnappschussErstellen() == Testdaten.schnappschuss(6, 4711L)
    }
}