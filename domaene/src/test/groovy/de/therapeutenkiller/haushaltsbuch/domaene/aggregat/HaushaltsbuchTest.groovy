package de.therapeutenkiller.haushaltsbuch.domaene.aggregat

import de.therapeutenkiller.coding.aspekte.ArgumentIstNullException
import de.therapeutenkiller.haushaltsbuch.domaene.aggregat.Haushaltsbuch
import spock.lang.Specification

class HaushaltsbuchTest extends Specification {

    def "Hinzufügen eines nicht extistierenden Kontos"() {

        given: "Angenommen ein neues Haushaltsbuch wurde angelegt"
        def haushaltsbuch = new Haushaltsbuch();

        when: "Wenn ich versuche eine nicht existierendes Konto hinzuzufügen"
        haushaltsbuch.neuesKontoHinzufügen(null)

        then: "Dann wird eine ArgumentIstNullException ausgelöst."
        thrown ArgumentIstNullException
    }

    def "Das Konto Anfangsbestand wird automatisch angelegt"() {

        given: "Angenommen ich lege ein neues Haushaltsbuch an"
        def haushaltsbuch = new Haushaltsbuch(UUID.randomUUID());

        expect: "Dann wird das Konto Anfangsbestand existieren"
        haushaltsbuch.kontoSuchen("Anfangsbestand") != null
    }
}
