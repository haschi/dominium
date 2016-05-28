package com.github.haschi.dominium.modell

import com.github.haschi.dominium.testdomäne.TestAggregat
import com.github.haschi.dominium.testdomäne.TestAggregatFabrik
import com.github.haschi.dominium.testdomäne.TestAggregatSchnappschuss
import com.github.haschi.dominium.testdomäne.ZustandWurdeGeändert
import spock.lang.Specification

class AggregatFabrikTest extends Specification {

    UUID identitätsmerkmal = UUID.randomUUID()

    def "Eine AggregatFabrik kann ein neues Aggregat erzeugen"() {
        given:
        TestAggregatFabrik fabrik = new TestAggregatFabrik();

        when:
        TestAggregat aggregat = fabrik.erzeugen(identitätsmerkmal)

        then:
        aggregat.schnappschussErstellen() == TestAggregatSchnappschuss.builder()
            .version(Version.NEU)
            .build()
    }

    def "Eine AggregatFabrik kann aus einem Ereignisstrom ein Aggregat erzeugen"() {
        given:
        TestAggregatFabrik fabrik = new TestAggregatFabrik();

        when:
        TestAggregat aggregat = fabrik.erzeugen(identitätsmerkmal, [ZustandWurdeGeändert.of(42L)])

        then:
        aggregat.schnappschussErstellen() == TestAggregatSchnappschuss.builder()
            .version(Version.NEU.nachfolger(1))
            .payload(42L)
            .build()
    }

    def "Eine AggregatFabrik kann aus einem Schnappschuss und einem Ereignisstrom ein Aggregat erzeugen"() {
        given:
        TestAggregatFabrik fabrik = new TestAggregatFabrik();
        TestAggregatSchnappschuss schnappschuss = TestAggregatSchnappschuss.builder()
            .version(Version.NEU.nachfolger(5))
            .payload(42L)
            .build()

        when:
        TestAggregat aggregat = fabrik.erzeugen(identitätsmerkmal, schnappschuss, [ZustandWurdeGeändert.of(4711L)])

        then:
        aggregat.schnappschussErstellen() == TestAggregatSchnappschuss.builder()
            .version(schnappschuss.version.nachfolger())
            .payload(4711L)
            .build()
    }
}