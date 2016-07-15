package com.github.haschi.haushaltsbuch.domaene.aggregat;

import com.github.haschi.coding.aspekte.ValueObject;
import com.github.haschi.haushaltsbuch.api.Kontoname;

import javax.money.MonetaryAmount;
import javax.money.format.MonetaryAmountFormat;
import javax.money.format.MonetaryFormats;
import java.util.Locale;

@ValueObject
public final class Buchungssatz {

    private final Kontoname sollkonto;
    private final Kontoname habenkonto;
    private final MonetaryAmount währungsbetrag;

    public Buchungssatz(final Kontoname sollkonto, final Kontoname habenkonto, final MonetaryAmount währungsbetrag) {
        super();

        if (währungsbetrag.isNegative()) {
            throw new IllegalArgumentException("Buchungssätze dürfen keine negativen Beträge besitzen.");
        }

        this.sollkonto = sollkonto;
        this.habenkonto = habenkonto;
        this.währungsbetrag = währungsbetrag;
    }

    public boolean hatSollkonto(final Kontoname konto) {
        return this.sollkonto.equals(konto);
    }

    public boolean hatHabenkonto(final Kontoname konto) {
        return this.habenkonto.equals(konto);
    }

    public Kontoname getSollkonto() {
        return this.sollkonto;
    }

    public Kontoname getHabenkonto() {
        return this.habenkonto;
    }

    public MonetaryAmount getWährungsbetrag() {
        return this.währungsbetrag;
    }

    public boolean istAnfangsbestandFür(final Kontoname konto) {
        return (this.habenkonto.equals(konto)
                && this.sollkonto.equals(Konto.ANFANGSBESTAND.getName()))
                || (this.habenkonto.equals(Konto.ANFANGSBESTAND.getName())
                && this.sollkonto.equals(konto));
    }

    @Override
    public String toString() {
        final MonetaryAmountFormat format = MonetaryFormats.getAmountFormat(Locale.GERMANY);
        final String betrag = format.format(this.währungsbetrag); // NOPMD LoD TODO

        return String.format("%s (%s) an %s (%s)", // NOPMD LoD TODO
                this.sollkonto.toString(),
                betrag,
                this.habenkonto.toString(),
                betrag);
    }
}
