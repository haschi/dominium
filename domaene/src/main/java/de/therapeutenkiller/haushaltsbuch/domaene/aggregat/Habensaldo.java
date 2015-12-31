package de.therapeutenkiller.haushaltsbuch.domaene.aggregat;

import javax.money.MonetaryAmount;
import javax.money.format.MonetaryAmountFormat;
import javax.money.format.MonetaryFormats;
import java.util.Locale;

public final class Habensaldo extends Saldo {

    @Override
    public MonetaryAmount getBetrag() {
        return this.betrag;
    }

    public Habensaldo(final MonetaryAmount betrag) {
        super(betrag);
    }

    @Override
    public String toString() {
        final MonetaryAmountFormat format = MonetaryFormats.getAmountFormat(Locale.GERMANY);
        final String betrag = format.format(this.getBetrag()); // NOPMD LoD TODO

        return "Habensaldo{" + betrag + "}";
    }
}
