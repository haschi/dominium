package com.github.haschi.haushaltsbuch.api.kommando;

import com.github.haschi.coding.aspekte.ValueObject;
import com.github.haschi.haushaltsbuch.api.Kontoart;

import javax.money.MonetaryAmount;
import java.util.UUID;

@ValueObject
public class LegeKontoMitAnfangsbestandAn {

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
