package de.therapeutenkiller.haushaltsbuch.api.kommando;

import de.therapeutenkiller.dominium.modell.Wertobjekt;
import de.therapeutenkiller.haushaltsbuch.api.Kontoart;

import javax.money.MonetaryAmount;
import java.util.UUID;

public class LegeKontoMitAnfangsbestandAn extends Wertobjekt {

    public final UUID haushaltsbuchId;
    public final String kontoname;
    public final Kontoart kontoart;
    public final MonetaryAmount betrag;

    public LegeKontoMitAnfangsbestandAn(
            final UUID haushaltsbuchId,
            final String kontoname,
            final Kontoart kontoart,
            final MonetaryAmount betrag) {

        super();

        this.haushaltsbuchId = haushaltsbuchId;
        this.kontoname = kontoname;
        this.kontoart = kontoart;
        this.betrag = betrag;
    }
}
