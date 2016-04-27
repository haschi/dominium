package de.therapeutenkiller.haushaltsbuch.api.kommando;

import de.therapeutenkiller.coding.annotation.Builder;
import de.therapeutenkiller.coding.aspekte.ValueObject;
import de.therapeutenkiller.haushaltsbuch.api.Kontoart;

import java.util.UUID;

@Builder
@ValueObject
public class LegeKontoAn {
    public final UUID haushaltsbuchId;
    public final String kontoname;
    public final Kontoart kontoart;

    public LegeKontoAn(final UUID haushaltsbuchId, final String kontoname, final Kontoart kontoart) {

        super();

        this.haushaltsbuchId = haushaltsbuchId;
        this.kontoname = kontoname;
        this.kontoart = kontoart;
    }
}
