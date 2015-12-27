package de.therapeutenkiller.haushaltsbuch.domaene.anwendungsfall;

import java.util.UUID;

public class KontoAnlegenKommando {
    public final UUID haushaltsbuch;
    public final String kontoname;

    public KontoAnlegenKommando(final UUID haushaltsbuch, final String kontoname) {

        this.haushaltsbuch = haushaltsbuch;
        this.kontoname = kontoname;
    }
}
