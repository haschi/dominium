package de.therapeutenkiller.dominium.memory

import de.therapeutenkiller.dominium.testdomäne.TestAggregat
import de.therapeutenkiller.dominium.testdomäne.TestAggregatSchnappschuss
import spock.lang.Specification

class SchnappschussLagerLeerenTest extends  Specification {

    def "Nach dem Leeren befinden sich keine Schnappschüsse im Lager"() {
        given:
        def lager = new MemorySchnappschussLager<TestAggregatSchnappschuss, TestAggregat, UUID>()
        def identitätsmerkmal = UUID.randomUUID()
        lager.schnappschussHinzufügen(TestAggregatSchnappschuss.builder().identitätsmerkmal(identitätsmerkmal).build())

        when:
        lager.leeren();

        then:
        !lager.getNeuesterSchnappschuss(identitätsmerkmal).isPresent()
    }
}
