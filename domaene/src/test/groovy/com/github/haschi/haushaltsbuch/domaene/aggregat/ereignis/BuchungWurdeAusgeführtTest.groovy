package com.github.haschi.haushaltsbuch.domaene.aggregat.ereignis

import nl.jqno.equalsverifier.EqualsVerifier
import nl.jqno.equalsverifier.Warning
import spock.lang.Specification

public class BuchungWurdeAusgeführtTest extends Specification {

    def "erfüllt die equals und hashCode Spezifikation"() {
        expect: EqualsVerifier.forClass ImmutableBuchungWurdeAusgeführt suppress(Warning.NULL_FIELDS) verify()
    }
}
