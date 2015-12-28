package de.therapeutenkiller.haushaltsbuch.api.ereignis

import nl.jqno.equalsverifier.EqualsVerifier
import spock.lang.Specification

/**
 * Created by matthias on 21.12.15.
 */
class KontoWurdeNichtAngelegtTest extends Specification {

    def "erfüllt die equals und hashCode Spezifikation"() {
        expect: EqualsVerifier.forClass KontoWurdeNichtAngelegt withRedefinedSuperclass() verify()
    }
}
