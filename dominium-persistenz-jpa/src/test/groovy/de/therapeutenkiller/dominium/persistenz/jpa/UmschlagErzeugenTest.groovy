package de.therapeutenkiller.dominium.persistenz.jpa

import de.therapeutenkiller.dominium.persistenz.jpa.aggregat.ZustandWurdeGeändert
import spock.lang.Shared
import spock.lang.Specification

class UmschlagErzeugenTest extends Specification {

    @Shared UUID id = UUID.randomUUID()

    def "Ein Umschlag wird beim registrieren von Ereignissen erzeugt"() {
        given: "ich habe einen Ereignis-Strom"
        JpaEreignisstrom ereignisstrom = new JpaEreignisstrom(id)

        when: "ich ein Ereignis registriere"
        def umschlag = ereignisstrom.registrieren(new ZustandWurdeGeändert(42L))

        then: "werde ich einen Umschlag für das Ereignis erhalten"
        umschlag.öffnen() == new ZustandWurdeGeändert(42L)
    }

    def "Der Umschlag eines registrierten Ereignisses enthält Meta-Daten"() {
        given: "ich habe einen Ereignis-Strom"
        JpaEreignisstrom ereignisstrom = new JpaEreignisstrom(id)

        when: "ich ein Ereignis registriere"
        def umschlag = ereignisstrom.registrieren(new ZustandWurdeGeändert(42L))

        then: "werde ich Meta-Daten um Umschlag des Ereignisses erhalten"
        umschlag.metaDaten.version == 1
        umschlag.metaDaten.identitätsmerkmal == id
    }
}
