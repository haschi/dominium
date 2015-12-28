package de.therapeutenkiller.haushaltsbuch.api.ereignis

import nl.jqno.equalsverifier.EqualsVerifier
import spock.lang.Specification

public final class HaushaltsbuchWurdeAngelegtTest extends Specification {

    def "erfüllt die equals und hashCode Spezfifikation"() {
        expect: EqualsVerifier
                .forClass(HaushaltsbuchWurdeAngelegt)
                .withRedefinedSuperclass()
                .verify()
    }
}
