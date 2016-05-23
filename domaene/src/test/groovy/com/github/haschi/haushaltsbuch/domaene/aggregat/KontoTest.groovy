package com.github.haschi.haushaltsbuch.domaene.aggregat

import nl.jqno.equalsverifier.EqualsVerifier
import org.apache.commons.lang3.StringUtils
import spock.lang.Specification
import spock.lang.Unroll

public class KontoTest extends Specification {

    def "erfüllt die equals und hashCode Spezifikation"() {
        expect: EqualsVerifier
                .forClass(Konto)
                .withIgnoredFields("regel")
                .verify()
    }

    @Unroll
    def "Der Kontoname darf nicht leer sein"(final String kontoname) {
        when:
        new Konto(kontoname, new KeineRegel())

        then:
        thrown IllegalArgumentException

        where:
        kontoname         | _
        StringUtils.EMPTY | _
        StringUtils.SPACE | _
    }
}
