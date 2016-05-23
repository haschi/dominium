package com.github.haschi.haushaltsbuch.domaene.aggregat.ereignis

import nl.jqno.equalsverifier.EqualsVerifier
import spock.lang.Specification

public final class KontoWurdeAngelegtTest extends Specification {

    def "erfüllt die equals und hashCode Spezifikation"() {
        expect: EqualsVerifier
                .forClass(KontoWurdeAngelegt)
                .withRedefinedSuperclass()
                .verify();
    }
}