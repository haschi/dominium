package de.therapeutenkiller.haushaltsbuch.domaene

import de.therapeutenkiller.haushaltsbuch.aspekte.ContractException
import spock.lang.Specification

/**
 * Created by mhaschka on 10.10.15.
 */
class NullPointerExceptionAspektTest extends Specification{

    def "Paramater dürfen keine null Pointer sein"() {
        given:def test = new TraceTest();
        when: test.methodeMitParameter(null)
        then: thrown(ContractException)
    }

    def "Methoden dürfen null nicht zurückgeben"() {
        given: def test = new TraceTest();
        when: test.methodeGibtNullZurück();
        then: thrown(ContractException)
    }

    def "Parameter mit CanBeNull Annotation"() {
        given: def test = new TraceTest();
        when: test.parameterCanBeNull(null);
        then: notThrown ContractException
    }
}
