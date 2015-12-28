package de.therapeutenkiller.haushaltsbuch.api.kommando;

import java.util.UUID;

public class KontoAnlegenKommando {
    public final UUID haushaltsbuchId;
    public final String kontoname;

    public KontoAnlegenKommando(final UUID haushaltsbuchId, final String kontoname) {

        this.haushaltsbuchId = haushaltsbuchId;
        this.kontoname = kontoname;
    }
}
