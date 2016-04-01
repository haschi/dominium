package de.therapeutenkiller.dominium.memory

import de.therapeutenkiller.dominium.modell.Schnappschuss
import de.therapeutenkiller.dominium.persistenz.AggregatNichtGefunden
import de.therapeutenkiller.dominium.persistenz.Magazin
import de.therapeutenkiller.dominium.testdomäne.TestAggregat
import de.therapeutenkiller.dominium.testdomäne.TestAggregatEreignis
import de.therapeutenkiller.dominium.testdomäne.TestAggregatEreignisziel
import de.therapeutenkiller.dominium.testdomäne.TestAggregatSchnappschuss
import spock.lang.Specification
import spock.lang.Unroll

class SchnappschüsseAblegenTest extends Specification {

    TestUhr uhr = new TestUhr()
    UUID identitätsmerkmal = UUID.randomUUID()
    Magazin<TestAggregat, TestAggregatEreignis, UUID, TestAggregatEreignisziel> magazin
    private MemorySchnappschussLager<TestAggregatSchnappschuss, TestAggregat, UUID> schnappschussLager

    def setup() {
        MemoryEreignislager<TestAggregatEreignis, UUID, TestAggregatEreignisziel> ereignislager =
                new MemoryEreignislager<>(uhr)

        schnappschussLager = new MemorySchnappschussLager<>()

        magazin = new Magazin<TestAggregat, TestAggregatEreignis, UUID, TestAggregatEreignisziel>(
            ereignislager, schnappschussLager) {
                @Override
                protected TestAggregat neuesAggregatErzeugen(UUID identitätsmerkmal) {
                    return new TestAggregat(identitätsmerkmal)
                }
            }
    }

    def "Schnappschüsse eines Aggregats können im Schnappschuss-Lager abgelegt werden"() {
        given:
        MemorySchnappschussLager<TestAggregatSchnappschuss, TestAggregat, UUID> lager = new MemorySchnappschussLager<>()

        Schnappschuss<TestAggregat, UUID> schnappschuss = TestAggregatSchnappschuss.builder()
                .identitätsmerkmal(identitätsmerkmal).build()

        when:
        lager.schnappschussHinzufügen(schnappschuss)

        then:
        lager.getNeuesterSchnappschuss(identitätsmerkmal).get() == schnappschuss
    }

    def "Schnappschüsse eines nicht existierenden Aggregats ablegen"() {
        given:

        Schnappschuss<TestAggregat, UUID> schnappschuss = TestAggregatSchnappschuss.builder()
                .identitätsmerkmal(identitätsmerkmal)
                .build()

        when:
        magazin.speichern(schnappschuss)

        then:
        thrown AggregatNichtGefunden
    }

    @Unroll
    def "Magazin stellt Aggregat aus Schnappschuss wieder her"() {
        given:
        TestAggregat aggregat = new TestAggregat(identitätsmerkmal)
        aggregat.zustandÄndern(1L)
        magazin.hinzufügen(aggregat)

        TestAggregatSchnappschuss schnappschuss = TestAggregatSchnappschuss.builder()
            .payload(payload)
            .identitätsmerkmal(identitätsmerkmal)
            .version(aggregat.version)
            .build()

        when:
        schnappschussLager.schnappschussHinzufügen(schnappschuss)

        then:
        magazin.suchen(identitätsmerkmal).zustand == payload;

        where:
        payload || _
        42L     || _
        43L     || _
    }
}
