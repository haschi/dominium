package de.therapeutenkiller.haushaltsbuch.domaene.aggregat.ereignis

import nl.jqno.equalsverifier.EqualsVerifier
import spock.lang.Specification

public final class KontoWurdeAngelegtTest extends Specification {

    def "erfüllt die equals und hashCode Spezifikation"() {
        expect: EqualsVerifier
                .forClass(KontoWurdeAngelegt)
                .withIgnoredFields("id")
                .withRedefinedSuperclass()
                .verify();
    }
}