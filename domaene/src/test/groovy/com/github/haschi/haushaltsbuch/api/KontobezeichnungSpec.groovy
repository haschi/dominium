package com.github.haschi.haushaltsbuch.api

import nl.jqno.equalsverifier.EqualsVerifier
import spock.lang.Specification


class KontobezeichnungSpec extends Specification {

    def "Kontobezeichnung darf nicht leer sein"() {
        when:
        Kontobezeichnung.of("")

        then:
        thrown KontobezeichnungWarUngültig
    }

    def "Kontobezeichnung darf nicht länger als 128 Zeichen sein"() {
        when:
        Kontobezeichnung.of(kontoname)

        then:
        thrown KontobezeichnungWarUngültig

        where:
        kontoname | _
        "X" * 130 | _
        "X" * 129 | _
        "X" * 999 | _
    }

    def "Kontobezeichnung zwischen 1 und 128 Zeichen sind erlaubt"() {
        when:
        Kontobezeichnung.of(kontoname)

        then:
        notThrown KontobezeichnungWarUngültig

        where:
        kontoname | _
        "X" * 1   | _
        "X" * 128 | _
    }

    def "Kontobezeichnung darf nicht ausschließlich Leerzeichen enthalten"() {
        when:
        Kontobezeichnung.of(kontoname)

        then:
        thrown KontobezeichnungWarUngültig

        where:
        kontoname             | _
        StringUtils.SPACE     | _
        StringUtils.SPACE * 2 | _
        StringUtils.LF        | _
    }

    def "Kontobezeichnung darf Leerzeichen enthalten"() {
        when:
        Kontobezeichnung.of(kontoname)

        then:
        notThrown KontobezeichnungWarUngültig

        where:
        kontoname                           | _
        "Ein Konto"                         | _
        "X" + StringUtils.SPACE * 126 + "X" | _
    }

    def "Kontobezeichnung darf Umlaute enthalten"() {
        when:
        Kontobezeichnung.of(kontoname)

        then:
        notThrown KontobezeichnungWarUngültig

        where:
        kontoname | _
        "öäüÖÄüß" | _
        "ÜÄ"      | _
    }

    def "Kontobezeichnung ist ein Wert"() {
        expect:
        EqualsVerifier.forClass(Kontobezeichnung).verify()
    }

    def "Kontobezeichnung hat Repräsentation als Zeichenkette"() {
        when:
        def kontobezeichnung = Kontobezeichnung.of("Ein Konto")

        then:
        kontobezeichnung.toString() == "Ein Konto"
    }
}