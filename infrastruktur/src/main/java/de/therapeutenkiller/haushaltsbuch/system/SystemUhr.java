package de.therapeutenkiller.haushaltsbuch.system;

import de.therapeutenkiller.dominium.persistenz.Uhr;

import java.time.LocalDateTime;

@SuppressWarnings("checkstyle:designforextension")
public class SystemUhr implements Uhr {

    public LocalDateTime jetzt() {
        return LocalDateTime.now();
    }
}
