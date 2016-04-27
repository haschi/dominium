package de.therapeutenkiller.dominium.modell

import de.therapeutenkiller.coding.aspekte.ArgumentIstNullException
import de.therapeutenkiller.dominium.testdomäne.EinWertobjekt
import de.therapeutenkiller.dominium.testdomäne.EinWertobjektBuilder
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

    def "Wertobjekt besitzt einen Builder"() {
        expect:
        new EinWertobjektBuilder() != null
    }

    def "Dem Wertobjekt dürfen keine Null-Werte übergeben werden"() {
        when:
        new EinWertobjekt(null, "Haschka");

        then:
        thrown ArgumentIstNullException
    }
}
