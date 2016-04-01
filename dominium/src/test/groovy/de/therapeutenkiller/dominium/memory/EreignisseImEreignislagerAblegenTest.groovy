package de.therapeutenkiller.dominium.memory

import de.therapeutenkiller.coding.aspekte.ArgumentIstNullException
import de.therapeutenkiller.dominium.testdomäne.TestAggregatEreignis
import de.therapeutenkiller.dominium.testdomäne.TestAggregatEreignisziel

import de.therapeutenkiller.dominium.persistenz.Versionsbereich
import de.therapeutenkiller.dominium.testdomäne.ZustandWurdeGeändert
import spock.lang.Specification
import spock.lang.Unroll

class EreignisseImEreignislagerAblegenTest extends Specification {

    TestUhr uhr = new TestUhr()
    UUID identitätsmerkmal = UUID.randomUUID()

    @Unroll
    def "Neue Ereignis-Ströme erzeugen"() {

        given: "ich habe ein Ereignislager"
        MemoryEreignislager<TestAggregatEreignis, UUID, TestAggregatEreignisziel> lager = new MemoryEreignislager<>()

        when: "ich einen neuen Ereignisstrom mit Ereignissen erzeuge"
        lager.neuenEreignisstromErzeugen(identitätsmerkmal, testEreignisse)

        then: "werde ich die Ereignisse für den Ereignisstrom aus dem Lager lesen können"
        def liste = lager.getEreignisliste(identitätsmerkmal, Versionsbereich.ALLE_VERSIONEN)
        liste == testEreignisse

        where:
        testEreignisse << [
                [new ZustandWurdeGeändert(42L), new ZustandWurdeGeändert(43L)],
                []
        ]
    }

    def "Ein neuer Ereignis-Strom darf nicht ohne Ereignis-Liste erzeugt werden"() {
        given:
        MemoryEreignislager<TestAggregatEreignis, UUID, TestAggregatEreignisziel> lager = new MemoryEreignislager<>()

        when:
        lager.neuenEreignisstromErzeugen(identitätsmerkmal, null)

        then:
        thrown ArgumentIstNullException
    }

    def "Ein neuer Ereignis-Strom darf nicht ohne Namen erzeugt werden"() {
        given:
        MemoryEreignislager<TestAggregatEreignis, UUID, TestAggregatEreignisziel> lager = new MemoryEreignislager<>()

        when:
        lager.neuenEreignisstromErzeugen(null, [])

        then:
        thrown ArgumentIstNullException
    }

    def "Doppelte Namen für Ereignis-Ströme sind nicht erlaubt"() {
        given:
        MemoryEreignislager<TestAggregatEreignis, UUID, TestAggregatEreignisziel> lager = new MemoryEreignislager<>()
        lager.neuenEreignisstromErzeugen(identitätsmerkmal, [])

        when:
        lager.neuenEreignisstromErzeugen(identitätsmerkmal, [])

        then:
        thrown IllegalArgumentException
    }
}
