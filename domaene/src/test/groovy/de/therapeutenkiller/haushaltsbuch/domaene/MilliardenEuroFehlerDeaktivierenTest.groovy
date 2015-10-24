package de.therapeutenkiller.haushaltsbuch.domaene

import de.therapeutenkiller.haushaltsbuch.aspekte.ArgumentIstNull
import de.therapeutenkiller.haushaltsbuch.aspekte.RückgabewertIstNull
import spock.lang.Ignore
import spock.lang.Narrative
import spock.lang.Specification

@Narrative("Wenn der MilliardenEuroFehlerDeaktivieren Aspekt aktiv ist")
public final class MilliardenEuroFehlerDeaktivierenTest extends Specification {

    def "Argumente dürfen nicht null sein"() {
        given:def test = new TraceTest();
        when: test.methodeMitParameter(null)
        then: thrown ArgumentIstNull
    }

    def "Methoden dürfen null nicht zurückgeben"() {
        given: def test = new TraceTest();

        when: test.methodeGibtNullZurück();

        then:
        RückgabewertIstNull ausnahme = thrown();
        ausnahme.message == "Rückgabewert der Methode 'methodeGibtNullZurück' ist null."
    }

    @Ignore
    def "Lamdas dürfen null nicht zurückgaben"() {

        given: def closure = {-> return null}
        when: closure()
        then:
        ArgumentIstNull ausnahme = thrown()
        ausnahme.message == "Rückagbewer der Methode 'x' ist null."
    }

    def "Methode dürfen mit DarfNullSein-Annoation null zurückgegeben"() {
        given: def test = new TraceTest();
        when: test.annotierteMethodeGibtNullZurück();
        then: notThrown RückgabewertIstNull;
    }

    def "Parameter mit CanBeNull Annotation"() {
        given: def test = new TraceTest();
        when: test.parameterCanBeNull(null);
        then: notThrown ArgumentIstNull
    }
}
