package de.therapeutenkiller.haushaltsbuch.domaene

import nl.jqno.equalsverifier.EqualsVerifier
import spock.lang.Specification

public final class KontoTest extends Specification {

    def "Konten erfüllen die equals und hashCode Konventionen"() {
        expect: EqualsVerifier.forClass(Konto.class)
            .usingGetClass()
            .verify()
    }
}
