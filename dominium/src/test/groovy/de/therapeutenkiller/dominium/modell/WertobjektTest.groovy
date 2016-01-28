package de.therapeutenkiller.dominium.modell

import nl.jqno.equalsverifier.EqualsVerifier
import spock.lang.Specification

class WertobjektTest extends Specification {

    final class EinWertobjekt extends Wertobjekt {
        final String vorname;
        final String nachname;
        final Date geburtsdatum;
    }

    def "Konkrete Wertobjekte erfüllen die Äquivalenzregeln"() {
        expect: EqualsVerifier.forClass(EinWertobjekt)
                .withRedefinedSuperclass()
                .verify()
    }
}
