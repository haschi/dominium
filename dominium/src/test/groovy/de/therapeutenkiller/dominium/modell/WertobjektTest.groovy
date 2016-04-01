package de.therapeutenkiller.dominium.modell

import nl.jqno.equalsverifier.EqualsVerifier
import spock.lang.Specification

class WertobjektTest extends Specification {

    final class EinWertobjekt extends Wertobjekt {
        final String vorname = "Matthias";
        final String nachname = "Haschka";
    }

    def "Konkrete Wertobjekte erfüllen die Äquivalenzregeln"() {
        expect: EqualsVerifier.forClass(EinWertobjekt)
                .withRedefinedSuperclass()
                .verify()
    }
}
