package com.github.haschi.haushaltsbuch.domaene.aggregat;

import com.github.haschi.coding.aspekte.ValueObject;
import com.github.haschi.haushaltsbuch.api.Kontoart;
import org.apache.commons.lang3.StringUtils;

import javax.money.MonetaryAmount;

@ValueObject(exclude = "regel")
public final class Konto {

    public static final Konto ANFANGSBESTAND = new Konto("Anfangsbestand", new KeineRegel(), Kontoart.Aktiv);

    private final String kontoname;

    private final Buchungsregel regel;
    private final Kontoart kontoart;

    public Konto(final String kontoname, final Buchungsregel regel, final Kontoart kontoart) {

        super();
        this.regel = regel;
        this.kontoart = kontoart;

        if (StringUtils.isBlank(kontoname)) {
            throw new IllegalArgumentException("Der Kontoname darf nicht leer sein");
        }

        this.kontoname = kontoname;
    }

    public String getBezeichnung() {
        return this.kontoname;
    }

    @Override
    public String toString() {
        return "Konto{" + "kontoname='" + this.kontoname + '\'' + '}';
    }

    @SuppressWarnings("checkstyle")
    public boolean kannAusgabeBuchen(final Buchungssatz buchungssatz) {
        return this.regel.kannErtragBuchen(buchungssatz);
    }

    public boolean kannTilgungGebuchtWerden(final Buchungssatz buchungssatz) {
        return this.regel.kannVerlustBuchen(buchungssatz);
    }

    public Buchungssatz buchungssatzFürAnfangsbestand(final MonetaryAmount betrag) {
        return this.regel.buchungssatzFürAnfangsbestand(this.kontoname, betrag);
    }

    public Kontoart getKontoart() {
        return this.kontoart;
    }
}
