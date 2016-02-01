package de.therapeutenkiller.dominium.memory

import de.therapeutenkiller.dominium.modell.testdomäne.TestAggregat
import de.therapeutenkiller.dominium.modell.testdomäne.TestAggregatSchnappschuss
import de.therapeutenkiller.dominium.persistenz.Uhr
import spock.lang.Specification

class AggregatAusSchnappschussWiederherstellen extends Specification {

    def "Aggregat aus Schnappschuss wiederherstellen"() {
        given:
        Uhr uhr = new TestUhr()
        MemoryEreignislager<TestAggregat, UUID> lager = new MemoryEreignislager<>(uhr)
        TestAggregat aggregat = new TestAggregat(UUID.randomUUID())
        aggregat.zustandÄndern(42L)

        lager.neuenEreignisstromErzeugen("test-strom", aggregat.änderungen)
        lager.schnappschussHinzufügen("test-strom", aggregat.schnappschussErstellen())

        when:
        def schnappschuss = lager.getNeuesterSchnappschuss("test-strom").get()
        def wiederhergestellt = schnappschuss.wiederherstellen()

        then:
        wiederhergestellt.zustand == 42L

    }
}
