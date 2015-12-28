package de.therapeutenkiller.haushaltsbuch.api.kommando;

import de.therapeutenkiller.haushaltsbuch.api.Kontoart;

import javax.money.MonetaryAmount;
import java.util.UUID;

public class KontoMitAnfangsbestandAnlegenKommando {

    public final UUID haushaltsbuchId;
    public final String kontoname;
    public final Kontoart kontoart;
    public final MonetaryAmount betrag;

    public KontoMitAnfangsbestandAnlegenKommando(
            final UUID haushaltsbuchId,
            final String kontoname,
            final Kontoart kontoart,
            final MonetaryAmount betrag) {
        this.haushaltsbuchId = haushaltsbuchId;
        this.kontoname = kontoname;
        this.kontoart = kontoart;
        this.betrag = betrag;
    }
}
