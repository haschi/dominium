package de.therapeutenkiller.haushaltsbuch.domaene

import de.therapeutenkiller.coding.aspekte.ArgumentIstNullException
import spock.lang.Specification


public final class GreeterTest extends Specification {

    def "wenn ich einen Namen eingebe, dann werde ich damit begrüßt"() {
        expect:
        Greeter.sayHello("Matthias") == "Hello!"
    }

    def "wenn ich keinen Namen eingebe, dann wird eine Ausnahme ausgelöst"() {
        when:
        Greeter.sayHello(null)
        then:
        thrown ArgumentIstNullException
    }
}
