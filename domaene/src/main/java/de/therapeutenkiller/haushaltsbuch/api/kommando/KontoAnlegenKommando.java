package de.therapeutenkiller.haushaltsbuch.api.kommando;

import de.therapeutenkiller.haushaltsbuch.api.Kontoart;
import de.therapeutenkiller.dominium.aggregat.Wertobjekt;

import java.util.UUID;

public class KontoAnlegenKommando extends Wertobjekt {
    public final UUID haushaltsbuchId;
    public final String kontoname;
    public final Kontoart kontoart;

    public KontoAnlegenKommando(final UUID haushaltsbuchId, final String kontoname, final Kontoart kontoart) {

        super();

        this.haushaltsbuchId = haushaltsbuchId;
        this.kontoname = kontoname;
        this.kontoart = kontoart;
    }
}
