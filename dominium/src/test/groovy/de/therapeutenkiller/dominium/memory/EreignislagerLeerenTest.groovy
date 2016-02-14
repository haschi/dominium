package de.therapeutenkiller.dominium.memory

import de.therapeutenkiller.dominium.modell.Schnappschuss
import de.therapeutenkiller.dominium.modell.testdomäne.TestAggregat
import de.therapeutenkiller.dominium.modell.testdomäne.TestAggregatSchnappschuss
import de.therapeutenkiller.dominium.modell.testdomäne.ZustandWurdeGeändert
import de.therapeutenkiller.dominium.persistenz.Versionsbereich
import spock.lang.Specification

class EreignislagerLeerenTest extends Specification {

    TestUhr uhr = new TestUhr()
    MemoryEreignislager<TestAggregat, UUID> lager = new MemoryEreignislager<>(uhr);

    def "Ereignisse des Ereignis-Lagers leeren"() {
        given:
        lager.neuenEreignisstromErzeugen("test-strom", [new ZustandWurdeGeändert(42L)])

        when:
        lager.clear()

        then:
        lager.getEreignisListe("test-strom", Versionsbereich.ALLE_VERSIONEN) == []
    }

    def "Schnappschüsse des Ereignis-Lagers leeren"() {
        given:
        Schnappschuss<TestAggregat, UUID> schnappschuss = new TestAggregatSchnappschuss()
        lager.neuenEreignisstromErzeugen("test-strom", [])
        lager.schnappschussHinzufügen("test-strom", schnappschuss)

        when:
        lager.clear()

        then:
        !lager.getNeuesterSchnappschuss("test-strom").isPresent()
    }

    def "Ereignis-Ströme des Ereignis-Lagers leeren"() {
        given:
        lager.neuenEreignisstromErzeugen("test-strom", [])

        when:
        lager.clear()
        lager.ereignisseDemStromHinzufügen("test-strom", 1L, [])

        then:
        thrown IllegalArgumentException
    }
}
