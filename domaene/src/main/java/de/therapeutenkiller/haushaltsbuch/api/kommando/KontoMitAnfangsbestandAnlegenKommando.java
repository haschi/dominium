package de.therapeutenkiller.haushaltsbuch.api.kommando;

import de.therapeutenkiller.haushaltsbuch.api.Kontoart;
import de.therapeutenkiller.haushaltsbuch.domaene.support.Wertobjekt;

import javax.money.MonetaryAmount;
import java.util.UUID;

public class KontoMitAnfangsbestandAnlegenKommando extends Wertobjekt{

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
