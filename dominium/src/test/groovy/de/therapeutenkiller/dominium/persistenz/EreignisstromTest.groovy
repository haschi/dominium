package de.therapeutenkiller.dominium.persistenz

import de.therapeutenkiller.dominium.modell.testdomäne.ZustandWurdeGeändert
import de.therapeutenkiller.dominium.persistenz.tools.TestEreignisMetaDaten
import de.therapeutenkiller.dominium.persistenz.tools.TestEreignisstrom
import spock.lang.Specification

class EreignisstromTest extends Specification {

    def "Ereignis-Ströme besitzen einen Namen"() {
        when:
        def strom = new TestEreignisstrom("test-strom")

        then:
        strom.name == "test-strom"
    }

    def "Ereignisse können für einen Ereignis-Strom registriert werden"() {

        given: "ich habe einen Ereignis-Strom angelegt"
        def strom = new TestEreignisstrom("test-strom")

        when: "ich eine Ereignis für den Ereignis-Strom registriere"
        def umschlag = strom.registrieren new ZustandWurdeGeändert(42L)

        then: "werde ich einen Umschlag für das Ereignis erhalten"
        umschlag.öffnen() == new ZustandWurdeGeändert(42L)

        then: "mit Meta-Daten für die Zuordnung zum Ereignisstrom"
        umschlag.metaDaten == new TestEreignisMetaDaten(1L, "test-strom")
    }
}
