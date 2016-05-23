package com.github.haschi.haushaltsbuch.domaene.aggregat.ereignis

import nl.jqno.equalsverifier.EqualsVerifier
import spock.lang.Specification

class BuchungWurdeAbgelehntTest extends Specification {

    def "erfüllt die equals und hashCode Spezifikation"() {
        expect: EqualsVerifier.forClass BuchungWurdeAbgelehnt withRedefinedSuperclass() verify()
    }

    def "Die Methode toString() liefert die Fehlermeldung zurück"() {
        given: "Ich habe ein BuchungWurdeAbgelehnt Ereignis"
        def ereignis = new BuchungWurdeAbgelehnt("Fehlermeldung.")

        when: "ich das Objekt als Zeichenfolge ausgebe"
        def ergebnis = ereignis.toString()

        then: "werde ich die Fehlermeldung erhalten"
        ergebnis == "Buchung wurde nicht ausgeführt: Fehlermeldung."
    }
}
