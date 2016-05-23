package com.github.haschi.haushaltsbuch.system;

import com.github.haschi.dominium.persistenz.Uhr;

import java.time.LocalDateTime;

@SuppressWarnings("checkstyle:designforextension")
public class SystemUhr implements Uhr {

    public LocalDateTime jetzt() {
        return LocalDateTime.now();
    }
}
