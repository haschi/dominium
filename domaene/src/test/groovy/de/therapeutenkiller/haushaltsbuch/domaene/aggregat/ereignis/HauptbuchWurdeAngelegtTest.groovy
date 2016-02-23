package de.therapeutenkiller.haushaltsbuch.domaene.aggregat.ereignis

import nl.jqno.equalsverifier.EqualsVerifier
import spock.lang.Specification

class HauptbuchWurdeAngelegtTest extends Specification {

    def "erfüllt die equals und hashCode Spezifikation"() {
        expect: EqualsVerifier.forClass HauptbuchWurdeAngelegt withRedefinedSuperclass() verify()
    }
}
