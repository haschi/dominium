package com.github.haschi.haushaltsbuch.api.ereignis

import com.github.haschi.haushaltsbuch.api.ImmutableBuchungWurdeAusgeführt
import nl.jqno.equalsverifier.EqualsVerifier
import nl.jqno.equalsverifier.Warning
import spock.lang.Specification

class BuchungWurdeAusgeführtTest extends Specification {

    def "erfüllt die equals und hashCode Spezifikation"() {
        expect:
        EqualsVerifier.forClass ImmutableBuchungWurdeAusgeführt suppress(Warning.NULL_FIELDS) verify()
    }
}
