package de.therapeutenkiller.dominium.modell

import de.therapeutenkiller.dominium.testdomäne.EinWertobjekt
import nl.jqno.equalsverifier.EqualsVerifier
import spock.lang.Specification

class WertobjektTest extends Specification {


    def "Konkrete Wertobjekte erfüllen die Äquivalenzregeln"() {
        expect: EqualsVerifier.forClass(EinWertobjekt).verify()
    }

    def "Wertobjekte geben sinnvollen String zurück"() {
        given:
        def wert = new EinWertobjekt("Matthias", "Haschka")

        when:
        def s = wert.toString()

        then:
        s == "EinWertobjekt[vorname=Matthias,nachname=Haschka]"
    }
}
