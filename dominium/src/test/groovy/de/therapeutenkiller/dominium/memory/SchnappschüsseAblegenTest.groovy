package de.therapeutenkiller.dominium.memory

import de.therapeutenkiller.dominium.modell.Schnappschuss
import de.therapeutenkiller.dominium.testdomäne.TestAggregat
import de.therapeutenkiller.dominium.testdomäne.TestAggregatEreignisziel
import de.therapeutenkiller.dominium.testdomäne.TestAggregatSchnappschuss
import spock.lang.Specification
import spock.lang.Unroll

class SchnappschüsseAblegenTest extends Specification {

    TestUhr uhr = new TestUhr()
    UUID identitätsmerkmal = UUID.randomUUID()

    def "Schnappschüsse eines existierenden Aggregats ablegen"() {
        given:
        MemoryEreignislager<TestAggregat, UUID, TestAggregatEreignisziel> lager = new MemoryEreignislager<>(uhr)
        lager.neuenEreignisstromErzeugen(identitätsmerkmal, [])
        Schnappschuss<TestAggregat, UUID> schnappschuss = new TestAggregatSchnappschuss()

        when:
        lager.schnappschussHinzufügen(identitätsmerkmal, schnappschuss)

        then:
        lager.getNeuesterSchnappschuss(identitätsmerkmal).get() == schnappschuss
    }

    def "Schnappschüsse eines nicht existierenden Aggregats ablegen"() {
        given:
        MemoryEreignislager<TestAggregat, UUID, TestAggregatEreignisziel> lager = new MemoryEreignislager<>(uhr)
        Schnappschuss<TestAggregat, UUID> schnappschuss = new TestAggregatSchnappschuss()

        when:
        lager.schnappschussHinzufügen(identitätsmerkmal, schnappschuss)

        then:
        thrown IllegalArgumentException
    }

    @Unroll
    def "Neuesten Schnappschuss ermitteln"() {
        given:
        MemoryEreignislager<TestAggregat, UUID, TestAggregatEreignisziel> lager = new MemoryEreignislager<>(uhr)
        lager.neuenEreignisstromErzeugen(identitätsmerkmal, [])

        schnappschüsse.each {
            String uhrzeit = (String)it[0]
            long payload = (long)it[1]

            def schnappschuss = TestAggregatSchnappschuss.builder()
                    .identitätsmerkmal(UUID.randomUUID())
                    .version(1)
                    .payload(payload)
                    .build()

            uhr.stellen uhrzeit
            lager.schnappschussHinzufügen(identitätsmerkmal, schnappschuss)
        }

        when:
        def neusterSchnappschuss = lager.getNeuesterSchnappschuss(identitätsmerkmal).get()
        def aggregat = neusterSchnappschuss.wiederherstellen()

        then:
        aggregat.zustand == payload;

        where:
        schnappschüsse << [
                [["2016-01-31T13:22:00Z", 42L], ["2016-01-31T13:21:00Z", 43L]],
                [["2016-01-31T13:22:00Z", 42L], ["2016-01-31T13:23:00Z", 43L]]
        ]

        payload << [42L, 43L]
    }
}
