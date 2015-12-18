package de.therapeutenkiller.haushaltsbuch.domaene.ereignis

import nl.jqno.equalsverifier.EqualsVerifier
import spock.lang.Specification

/**
 * Created by matthias on 18.12.15.
 */
public class BuchungssatzWurdeErstelltTest extends Specification {

    def "erfüllt die equals und hashCode Spezifikation"() {
        expect: EqualsVerifier
                .forClass(BuchungssatzWurdeErstellt)
                .withRedefinedSuperclass()
                .verify()
    }
}
