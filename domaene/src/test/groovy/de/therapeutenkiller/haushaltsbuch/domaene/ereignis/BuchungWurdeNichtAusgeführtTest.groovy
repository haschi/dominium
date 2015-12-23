package de.therapeutenkiller.haushaltsbuch.domaene.ereignis

import nl.jqno.equalsverifier.EqualsVerifier
import spock.lang.Specification

/**
 * Created by matthias on 21.12.15.
 */
class BuchungWurdeNichtAusgeführtTest extends Specification {

    def "erfüllt die equals und hashCode Spezifikation"() {
        expect: EqualsVerifier
                .forClass(BuchungWurdeNichtAusgeführt)
                .withRedefinedSuperclass()
                .verify()
    }

    def "Die Methode toString() liefert die Fehlermeldung zurück"() {
        given: "Ich habe ein BuchungWurdeNichtAusgeführt Ereignis"
        def ereignis = new BuchungWurdeNichtAusgeführt("Fehlermeldung.")

        when: "ich das Objekt als Zeichenfolge ausgebe"
        def ergebnis = ereignis.toString()

        then: "werde ich die Fehlermeldung erhalten"
        ergebnis == "Buchung wurde nicht ausgeführt: Fehlermeldung."
    }
}
