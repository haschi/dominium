package de.therapeutenkiller.dominium.memory

import de.therapeutenkiller.dominium.testdomäne.TestAggregatEreignis
import de.therapeutenkiller.dominium.testdomäne.TestAggregatEreignisziel

import de.therapeutenkiller.dominium.persistenz.KonkurrierenderZugriff
import de.therapeutenkiller.dominium.persistenz.Uhr
import de.therapeutenkiller.dominium.testdomäne.ZustandWurdeGeändert
import spock.lang.Specification

class KonkurrierendeZugriffePrüfenTest extends  Specification {

    Uhr uhr = new TestUhr()

    def "Konkurrierende Zugriffe werden beim Hinzufügen weiterer Ereignisse geprüft"() {
        given: "ich habe ein Ereignis-Lager mit Versionsstand 2"
        UUID identitätsmerkmal = UUID.randomUUID()
        MemoryEreignislager<TestAggregatEreignis, UUID, TestAggregatEreignisziel> lager = new MemoryEreignislager<>()
        lager.neuenEreignisstromErzeugen(identitätsmerkmal, [new ZustandWurdeGeändert(42L)])

        when: "ich die Version 0 beim Hinzufügen weiterer Ereignisse erwarte"
        lager.ereignisseDemStromHinzufügen identitätsmerkmal, version, []

        then: "werde ich eine Ausnahme erhalten"
        thrown KonkurrierenderZugriff

        where:
        version || _
        0L      || _
        2L      || _
    }
}
