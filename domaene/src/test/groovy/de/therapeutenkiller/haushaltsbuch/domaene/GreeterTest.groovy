package de.therapeutenkiller.haushaltsbuch.domaene

import spock.lang.Specification

/**
 * Created by mhaschka on 21.09.15.
 */
public class GreeterTest extends Specification{

    def "wenn ich einen Namen eingebe, dann werde ich damit begrüßt"() {
        expect: Greeter.sayHello("Matthias") == "Hello, Matthias!"
    }
}
