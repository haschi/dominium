package com.github.haschi.dominium.persistenz

import com.github.haschi.dominium.persistenz.tools.TestEreignisMetaDaten
import com.github.haschi.dominium.persistenz.tools.TestEreignisstrom
import com.github.haschi.dominium.testdomaene.ZustandWurdeGeändert
import spock.lang.Specification

class EreignisstromTest extends Specification {

    def "Ereignis-Ströme besitzen einen Namen"() {
        when:
        def strom = new TestEreignisstrom("test-strom")

        then:
        strom.identitätsmerkmal == "test-strom"
    }

    def "Ereignisse können für einen Ereignis-Strom registriert werden"() {

        given: "ich habe einen Ereignis-Strom angelegt"
        def strom = new TestEreignisstrom("test-strom")

        when: "ich eine Ereignis für den Ereignis-Strom registriere"
        def umschlag = strom.registrieren(ZustandWurdeGeändert.of(42L))

        then: "werde ich einen Umschlag für das Ereignis erhalten"
        umschlag.öffnen() == ZustandWurdeGeändert.of(42L)

        then: "mit Meta-Daten für die Zuordnung zum Ereignisstrom"
        umschlag.metaDaten == new TestEreignisMetaDaten(1L, "test-strom")
    }
}
