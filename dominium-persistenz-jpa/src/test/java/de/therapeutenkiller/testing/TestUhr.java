package de.therapeutenkiller.testing;

import de.therapeutenkiller.dominium.persistenz.Uhr;

import java.time.LocalDateTime;

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
