package de.therapeutenkiller.haushaltsbuch.domaene.aggregat.ereignis

import nl.jqno.equalsverifier.EqualsVerifier
import spock.lang.Specification

class JournalWurdeAngelegtTest extends Specification {

    def "Erfüllt die equals und hashCode Spezifikation"() {
        expect:
        EqualsVerifier.forClass JournalWurdeAngelegt withIgnoredFields "id" withRedefinedSuperclass() verify()
    }
}
