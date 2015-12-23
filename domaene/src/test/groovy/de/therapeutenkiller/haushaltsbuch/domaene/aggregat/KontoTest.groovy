package de.therapeutenkiller.haushaltsbuch.domaene.aggregat

import nl.jqno.equalsverifier.EqualsVerifier
import org.apache.commons.lang3.StringUtils
import spock.lang.Specification
import spock.lang.Unroll

public class KontoTest extends Specification {

    def "erfüllt die equals und hashCode Spezifikation"() {
        expect: EqualsVerifier
                .forClass(Konto)
                .withRedefinedSuperclass()
                .verify()
    }

    @Unroll
    def "Der Kontoname darf nicht leer sein"(final String kontoname) {
        when:
        new Konto(kontoname)

        then:
        thrown IllegalArgumentException

        where:
        kontoname         | _
        StringUtils.EMPTY | _
        StringUtils.SPACE | _
    }
}
