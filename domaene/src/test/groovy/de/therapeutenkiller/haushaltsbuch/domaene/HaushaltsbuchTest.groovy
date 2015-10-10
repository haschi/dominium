package de.therapeutenkiller.haushaltsbuch.domaene

import spock.lang.Specification

/**
 * Created by mhaschka on 10.10.15.
 */

class HaushaltsbuchTest extends Specification {
    def "Wenn ein neues Konto hinzugefügt wird, dann muss das Konto existieren"() {
            given: def haushaltsbuch = new Haushaltsbuch();
            when: haushaltsbuch.neuesKontoHinzufügen(null)
            then: thrown(NullPointerException)
    }
}
l
