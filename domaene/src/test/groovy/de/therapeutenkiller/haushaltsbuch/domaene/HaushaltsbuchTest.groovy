package de.therapeutenkiller.haushaltsbuch.domaene

import de.therapeutenkiller.haushaltsbuch.aspekte.ContractException
import spock.lang.Specification

/**
 * Created by mhaschka on 10.10.15.
 */

class HaushaltsbuchTest extends Specification {

    def "Hinzufügen eines nicht extistierenden Kontos"() {

        given: "Angenommen ein neues Haushaltsbuch wurde angelegt"
        def haushaltsbuch = new Haushaltsbuch();

        when: "Wenn ich versuche eine nicht existierendes Konto hinzuzufügen"
        haushaltsbuch.neuesKontoHinzufügen(null)

        then: "Dann wird eine NullPointerException ausgelöst."
        thrown(ContractException)
    }
}
