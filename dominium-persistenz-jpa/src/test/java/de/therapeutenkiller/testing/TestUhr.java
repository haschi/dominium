package de.therapeutenkiller.testing;

import de.therapeutenkiller.dominium.persistenz.Uhr;

import javax.annotation.Priority;
import javax.enterprise.inject.Alternative;
import javax.interceptor.Interceptor;
import java.time.LocalDateTime;

@Alternative
@Priority(Interceptor.Priority.APPLICATION + 10)
public final class TestUhr implements Uhr {
    LocalDateTime jetzt;

    @Override
    public LocalDateTime jetzt() {
        return this.jetzt;
    }

    public void stellen(final LocalDateTime jetzt) {
        this.jetzt = jetzt;
    }
}
