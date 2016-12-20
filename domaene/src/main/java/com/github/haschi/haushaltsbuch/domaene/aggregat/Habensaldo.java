package com.github.haschi.haushaltsbuch.domaene.aggregat;

import javax.money.MonetaryAmount;
import javax.money.format.MonetaryAmountFormat;
import javax.money.format.MonetaryFormats;
import java.util.Locale;

public final class Habensaldo
        extends Saldo
{

    public Habensaldo(final MonetaryAmount betrag)
    {
        super(betrag);
    }

    @Override
    public MonetaryAmount getBetrag()
    {
        return this.betrag;
    }

    @Override
    public Saldo soll(final MonetaryAmount buchungssatz)
    {
        if (this.betrag.isEqualTo(buchungssatz))
        {
            return new SollHabenSaldo();
        }

        if (this.betrag.isGreaterThan(buchungssatz))
        {
            return new Habensaldo(this.betrag.subtract(buchungssatz));
        }

        return new Sollsaldo(buchungssatz.subtract(this.betrag));
    }

    @Override
    public Saldo haben(final MonetaryAmount währungsbetrag)
    {
        return new Habensaldo(this.betrag.add(währungsbetrag));
    }

    @Override
    public String toString()
    {
        final MonetaryAmountFormat format = MonetaryFormats.getAmountFormat(Locale.GERMANY);
        final String betrag = format.format(this.getBetrag());
        return "Habensaldo{" + betrag + "}";
    }
}
