package de.therapeutenkiller.haushaltsbuch.domaene.aggregat.ereignis

import nl.jqno.equalsverifier.EqualsVerifier
import spock.lang.Specification

public final class HaushaltsbuchWurdeAngelegtTest extends Specification {

    def "erfüllt die equals und hashCode Spezifikation"() {
        expect: EqualsVerifier
                .forClass(HaushaltsbuchWurdeAngelegt)
                .withRedefinedSuperclass()
                .verify()
    }
}
