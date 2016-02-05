package de.therapeutenkiller.dominium.persistenz.jpa

import de.therapeutenkiller.dominium.persistenz.Uhr
import groovy.transform.CompileStatic

import java.time.ZonedDateTime

@CompileStatic
class TestUhr implements  Uhr{
    @Override
    ZonedDateTime jetzt() {
        return null
    }
}
