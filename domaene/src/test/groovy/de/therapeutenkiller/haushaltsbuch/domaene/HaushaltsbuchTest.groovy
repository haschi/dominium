package de.therapeutenkiller.haushaltsbuch.domaene

import de.therapeutenkiller.haushaltsbuch.aspekte.ArgumentIstNull
import spock.lang.Specification

class HaushaltsbuchTest extends Specification {

    def "Hinzufügen eines nicht extistierenden Kontos"() {

        given: "Angenommen ein neues Haushaltsbuch wurde angelegt"
        def haushaltsbuch = new Haushaltsbuch();

        when: "Wenn ich versuche eine nicht existierendes Konto hinzuzufügen"
        haushaltsbuch.neuesKontoHinzufügen(null, null)

        then: "Dann wird eine NullPointerException ausgelöst."
        thrown(ArgumentIstNull)
    }
}
