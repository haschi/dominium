package de.therapeutenkiller.haushaltsbuch.domaene.api;

import javax.money.MonetaryAmount;
import java.util.UUID;

public class KontoMitAnfangsbestandAnlegenKommando {

    public final UUID haushaltsbuchId;
    public final String kontoname;
    public final MonetaryAmount betrag;

    public KontoMitAnfangsbestandAnlegenKommando(
            final UUID haushaltsbuchId,
            final String kontoname,
            final MonetaryAmount betrag) {
        this.haushaltsbuchId = haushaltsbuchId;
        this.kontoname = kontoname;
        this.betrag = betrag;
    }
}
