package de.therapeutenkiller.dominium.memory

import de.therapeutenkiller.dominium.persistenz.Uhr

import java.time.Clock
import java.time.Instant
import java.time.ZoneId
import java.time.ZonedDateTime

class TestUhr implements Uhr {

    private static Clock uhr = Clock.fixed(
            Instant.parse("2015-10-14T14:54:21Z"),
            ZoneId.systemDefault().normalized());

    @Override
    public ZonedDateTime jetzt() {
        return ZonedDateTime.now(this.uhr);
    }

    public static void stellen(final String uhrzeit) {
        uhr = Clock.fixed(
                Instant.parse(uhrzeit),
                ZoneId.systemDefault().normalized());
    }
}
