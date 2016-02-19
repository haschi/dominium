package de.therapeutenkiller.haushaltsbuch.domaene.aggregat

import de.therapeutenkiller.coding.aspekte.ArgumentIstNullException
import de.therapeutenkiller.haushaltsbuch.domaene.aggregat.Haushaltsbuch
import spock.lang.Specification

class NeuesHaushaltsbuchAnlegenTest extends Specification {

    def "Hinzufügen eines nicht existierenden Kontos"() {

        given: "Angenommen ein neues Haushaltsbuch wurde angelegt"
        def haushaltsbuch = new Haushaltsbuch(UUID.randomUUID());

        when: "Wenn ich versuche eine nicht existierendes Konto hinzuzufügen"
        haushaltsbuch.neuesKontoHinzufügen(null)

        then: "Dann wird eine ArgumentIstNullException ausgelöst."
        thrown ArgumentIstNullException
    }
}
