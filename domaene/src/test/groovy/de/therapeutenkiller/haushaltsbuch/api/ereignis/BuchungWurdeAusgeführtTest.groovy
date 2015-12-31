package de.therapeutenkiller.haushaltsbuch.api.ereignis

import nl.jqno.equalsverifier.EqualsVerifier
import spock.lang.Specification

public class BuchungWurdeAusgeführtTest extends Specification {

    def "erfüllt die equals und hashCode Spezifikation"() {
        expect: EqualsVerifier
                .forClass(BuchungWurdeAusgeführt)
                .withRedefinedSuperclass()
                .verify()
    }
}
