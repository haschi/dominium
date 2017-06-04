package com.github.haschi.haushaltsbuch.api.ereignis

import com.github.haschi.haushaltsbuch.api.ImmutableKontoWurdeNichtAngelegt
import nl.jqno.equalsverifier.EqualsVerifier
import nl.jqno.equalsverifier.Warning
import spock.lang.Specification

class KontoWurdeNichtAngelegtTest extends Specification {

    def "erfüllt die equals und hashCode Spezifikation"() {
        expect:
        EqualsVerifier.forClass ImmutableKontoWurdeNichtAngelegt suppress(Warning.NULL_FIELDS) verify()
    }
}
