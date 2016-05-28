package com.github.haschi.dominium.modell

import com.github.haschi.dominium.testdomäne.TestAggregat
import com.github.haschi.dominium.testdomäne.TestAggregatFabrik
import com.github.haschi.dominium.testdomäne.TestAggregatSchnappschuss
import spock.lang.Specification


class AggregatFabrikTest extends Specification {

    UUID identitätsmerkmal = UUID.randomUUID()

    def "Eine AggregatFabrik kann ein neues Aggregat erzeugen"() {
        given:
        AggregatFabrik<TestAggregat, UUID> fabrik = new TestAggregatFabrik();

        when:
        TestAggregat aggregat = fabrik.erzeugen(identitätsmerkmal)

        then:
        aggregat.schnappschussErstellen() == TestAggregatSchnappschuss.builder()
            .identitätsmerkmal(identitätsmerkmal)
            .version(Version.NEU)
            .build()
    }
}