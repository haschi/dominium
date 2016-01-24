package de.therapeutenkiller.dominium.aggregat

import nl.jqno.equalsverifier.EqualsVerifier
import spock.lang.Specification

class WertobjektTest extends Specification {

    final class A extends Wertobjekt {
        final String vorname;
        final String nachname;
        final Date geburtsdatum;
    }

    def "Konkrete Wertobjekte erfüllen die Äquivalenzregeln"() {
        expect: EqualsVerifier.forClass(A)
                .withRedefinedSuperclass()
                .verify()
    }
}
