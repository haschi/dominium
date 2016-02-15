package de.therapeutenkiller.dominium.persistenz.jpa

import de.therapeutenkiller.dominium.persistenz.jpa.testaggregat.ZustandWurdeGeändert
import spock.lang.Specification

class UmschlagErzeugenTest extends Specification {

    def "Ein Umschlag wird beim registrieren von Ereignissen erzeugt"() {
        given: "ich habe einen Ereignis-Strom"
        JpaEreignisstrom ereignisstrom = new JpaEreignisstrom("test-strom")

        when: "ich ein Ereignis registriere"
        def umschlag = ereignisstrom.registrieren(new ZustandWurdeGeändert(42L))

        then: "werde ich einen Umschlag für das Ereignis erhalten"
        umschlag.öffnen() == new ZustandWurdeGeändert(42L)
    }

    def "Der Umschlag eines registrierten Ereignisses enthält Meta-Daten"() {
        given: "ich habe einen Ereignis-Strom"
        JpaEreignisstrom ereignisstrom = new JpaEreignisstrom("test-strom")

        when: "ich ein Ereignis registriere"
        def umschlag = ereignisstrom.registrieren(new ZustandWurdeGeändert(42L))

        then: "werde ich Meta-Daten um Umschlag des Ereignisses erhalten"
        umschlag.metaDaten.version == 1
        umschlag.metaDaten.name == "test-strom"
    }
}
