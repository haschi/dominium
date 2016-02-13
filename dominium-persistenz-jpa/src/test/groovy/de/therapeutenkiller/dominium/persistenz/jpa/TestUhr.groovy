package de.therapeutenkiller.dominium.persistenz.jpa

import de.therapeutenkiller.dominium.persistenz.Uhr
import groovy.transform.CompileStatic

import java.time.LocalDateTime
import java.time.ZonedDateTime

@CompileStatic
class TestUhr implements  Uhr {
    LocalDateTime jetzt;

    @Override
    LocalDateTime jetzt() {
        return jetzt
    }

    void stellen(LocalDateTime jetzt) {
        this.jetzt = jetzt;
    }
}
