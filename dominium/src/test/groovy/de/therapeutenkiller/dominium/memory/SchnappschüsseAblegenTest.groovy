package de.therapeutenkiller.dominium.memory

import de.therapeutenkiller.dominium.modell.Schnappschuss
import de.therapeutenkiller.dominium.modell.testdomäne.TestAggregat
import de.therapeutenkiller.dominium.modell.testdomäne.TestAggregatSchnappschuss
import spock.lang.Specification
import spock.lang.Unroll

class SchnappschüsseAblegenTest extends Specification {

    TestUhr uhr = new TestUhr()

    def "Schnappschüsse eines existierenden Aggregats ablegen"() {
        given:
        MemoryEreignislager<TestAggregat, UUID> lager = new MemoryEreignislager<>(uhr)
        lager.neuenEreignisstromErzeugen("test-strom", [])
        Schnappschuss<TestAggregat, UUID> schnappschuss = new TestAggregatSchnappschuss()

        when:
        lager.schnappschussHinzufügen("test-strom", schnappschuss)

        then:
        lager.getNeuesterSchnappschuss("test-strom").get() == schnappschuss
    }

    def "Schnappschüsse eines nicht existierenden Aggregats ablegen"() {
        given:
        MemoryEreignislager<TestAggregat, UUID> lager = new MemoryEreignislager<>(uhr)
        Schnappschuss<TestAggregat, UUID> schnappschuss = new TestAggregatSchnappschuss()

        when:
        lager.schnappschussHinzufügen("test-strom", schnappschuss)

        then:
        thrown IllegalArgumentException
    }

    @Unroll
    def "Neuesten Schnappschuss ermitteln"() {
        given:
        MemoryEreignislager<TestAggregat, UUID> lager = new MemoryEreignislager<>(uhr)
        lager.neuenEreignisstromErzeugen("test-strom", [])

        schnappschüsse.each {
            String uhrzeit = (String)it[0]
            long payload = (long)it[1]

            def schnappschuss = TestAggregatSchnappschuss.builder()
                    .identitätsmerkmal(UUID.randomUUID())
                    .version(1)
                    .payload(payload)
                    .build()

            uhr.stellen uhrzeit
            lager.schnappschussHinzufügen("test-strom", schnappschuss)
        }

        when:
        def neusterSchnappschuss = lager.getNeuesterSchnappschuss("test-strom").get()
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
