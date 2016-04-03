package de.therapeutenkiller.dominium.memory

import de.therapeutenkiller.dominium.testdomäne.TestAggregat
import de.therapeutenkiller.dominium.testdomäne.TestAggregatEreignis
import de.therapeutenkiller.dominium.testdomäne.TestAggregatEreignisziel
import de.therapeutenkiller.dominium.persistenz.Uhr
import de.therapeutenkiller.dominium.testdomäne.TestAggregatSchnappschuss
import spock.lang.Specification

class AggregatAusSchnappschussWiederherstellenTest extends Specification {

    def "Aggregat aus Schnappschuss wiederherstellen"() {
        given:
        def lager = new MemorySchnappschussLager<TestAggregat, UUID>()

        def identitätsmerkmal = UUID.randomUUID()

        lager.schnappschussHinzufügen(
                TestAggregatSchnappschuss.builder()
                        .identitätsmerkmal(identitätsmerkmal)
                        .payload(42L)
                        .version(1L)
                        .build())

        when:
        def schnappschuss = lager.getNeuesterSchnappschuss(identitätsmerkmal).get()
        def wiederhergestellt = schnappschuss.wiederherstellen()

        then:
        wiederhergestellt.zustand == 42L
    }
}
