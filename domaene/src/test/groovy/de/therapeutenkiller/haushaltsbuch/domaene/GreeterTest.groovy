package de.therapeutenkiller.haushaltsbuch.domaene

import de.therapeutenkiller.haushaltsbuch.aspekte.ContractException
import spock.lang.Specification


public class GreeterTest extends Specification {

    def "wenn ich einen Namen eingebe, dann werde ich damit begrüßt"() {
        expect:
        Greeter.sayHello("Matthias") == "Hello!"
    }

    def "wenn ich keinen Namen eingebe, dann wird eine Ausnahme ausgelöst"() {
        when:
        Greeter.sayHello(null)
        then:
        thrown(ContractException)
    }
}
