package com.github.haschi.haushaltsbuch.api.ereignis

import nl.jqno.equalsverifier.EqualsVerifier
import nl.jqno.equalsverifier.Warning
import spock.lang.Specification

class JournalWurdeAngelegtTest extends Specification {

    def "Erfüllt die equals und hashCode Spezifikation"() {
        expect:
        EqualsVerifier.forClass ImmutableJournalWurdeAngelegt suppress(Warning.NULL_FIELDS) verify()
    }
}
