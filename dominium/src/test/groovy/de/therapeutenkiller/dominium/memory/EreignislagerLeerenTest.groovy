package de.therapeutenkiller.dominium.memory

import de.therapeutenkiller.dominium.testdomäne.TestAggregatEreignis
import de.therapeutenkiller.dominium.testdomäne.TestAggregatEreignisziel

import de.therapeutenkiller.dominium.persistenz.Versionsbereich
import de.therapeutenkiller.dominium.testdomäne.ZustandWurdeGeändert
import spock.lang.Specification

class EreignislagerLeerenTest extends Specification {

    TestUhr uhr = new TestUhr()
    MemoryEreignislager<TestAggregatEreignis, UUID, TestAggregatEreignisziel> lager = new MemoryEreignislager<>();
    UUID identitätsmerkmal = UUID.randomUUID()

    def "Ereignisse des Ereignis-Lagers leeren"() {
        given:
        lager.neuenEreignisstromErzeugen(identitätsmerkmal, [new ZustandWurdeGeändert(42L)])

        when:
        lager.clear()

        then:
        lager.getEreignisliste(identitätsmerkmal, Versionsbereich.ALLE_VERSIONEN) == []
    }

    def "Ereignis-Ströme des Ereignis-Lagers leeren"() {
        given:
        lager.neuenEreignisstromErzeugen(identitätsmerkmal, [])

        when:
        lager.clear()
        lager.ereignisseDemStromHinzufügen(identitätsmerkmal, 1L, [])

        then:
        thrown IllegalArgumentException
    }
}
