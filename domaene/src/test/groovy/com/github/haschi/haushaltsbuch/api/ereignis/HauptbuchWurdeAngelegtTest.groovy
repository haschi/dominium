package com.github.haschi.haushaltsbuch.api.ereignis

import nl.jqno.equalsverifier.EqualsVerifier
import nl.jqno.equalsverifier.Warning
import spock.lang.Specification

class HauptbuchWurdeAngelegtTest extends Specification {

    def "erfüllt die equals und hashCode Spezifikation"() {
        expect:
        EqualsVerifier.forClass ImmutableHauptbuchWurdeAngelegt suppress(Warning.NULL_FIELDS) verify()
    }
}
