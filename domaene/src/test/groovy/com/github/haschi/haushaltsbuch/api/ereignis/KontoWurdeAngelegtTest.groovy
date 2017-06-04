package com.github.haschi.haushaltsbuch.api.ereignis

import com.github.haschi.haushaltsbuch.api.ImmutableKontoWurdeAngelegt
import nl.jqno.equalsverifier.EqualsVerifier
import nl.jqno.equalsverifier.Warning
import spock.lang.Specification

final class KontoWurdeAngelegtTest extends Specification {

    def "erfüllt die equals und hashCode Spezifikation"() {
        expect:
        EqualsVerifier
                .forClass(ImmutableKontoWurdeAngelegt)
                .suppress(Warning.NULL_FIELDS)
                .verify()
    }
}