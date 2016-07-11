package com.github.haschi.haushaltsbuch.domaene.aggregat;

import javax.money.MonetaryAmount;
import javax.money.format.MonetaryAmountFormat;
import javax.money.format.MonetaryFormats;
import java.util.Locale;

public final class Sollsaldo extends Saldo {
    @Override
    public MonetaryAmount getBetrag() {
        return this.betrag;
    }

    public Sollsaldo(final MonetaryAmount betrag) {
        super(betrag);
    }

    @Override
    public Saldo soll(final MonetaryAmount buchungssatz) {
        return new Sollsaldo(this.betrag.add(buchungssatz));
    }

    @Override
    public Saldo haben(final MonetaryAmount währungsbetrag) {
        if (this.betrag.isEqualTo(währungsbetrag)) {
            return new SollHabenSaldo();
        }

        if (this.betrag.isLessThan(währungsbetrag)) {
            return new Habensaldo(währungsbetrag.subtract(this.betrag));
        }

        return new Sollsaldo(this.betrag.subtract(währungsbetrag));
    }

    @Override
    public String toString() {
        final MonetaryAmountFormat format = MonetaryFormats.getAmountFormat(Locale.GERMANY);
        final String betrag = format.format(this.getBetrag()); // NOPMD LoD TODO

        return "Sollsaldo{" + betrag + "}";
    }
}
