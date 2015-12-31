package de.therapeutenkiller.haushaltsbuch.api.kommando;

import de.therapeutenkiller.haushaltsbuch.api.Kontoart;

import java.util.UUID;

public class KontoAnlegenKommando {
    public final UUID haushaltsbuchId;
    public final String kontoname;
    public Kontoart kontoart;

    public KontoAnlegenKommando(final UUID haushaltsbuchId, final String kontoname, final Kontoart kontoart) {

        this.haushaltsbuchId = haushaltsbuchId;
        this.kontoname = kontoname;
        this.kontoart = kontoart;
    }
}
