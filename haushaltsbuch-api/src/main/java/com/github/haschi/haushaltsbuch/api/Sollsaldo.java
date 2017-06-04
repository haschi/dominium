package com.github.haschi.haushaltsbuch.api;

import javax.money.MonetaryAmount;
import javax.money.format.MonetaryAmountFormat;
import javax.money.format.MonetaryFormats;
import java.util.Locale;

public final class Sollsaldo
        extends Saldo
{
    public Sollsaldo(final MonetaryAmount betrag)
    {
        super(betrag);
    }

    @Override
    public MonetaryAmount getBetrag()
    {
        return this.betrag;
    }

    @Override
    public Saldo soll(final MonetaryAmount geldbetrag)
    {
        return new Sollsaldo(this.betrag.add(geldbetrag));
    }

    @Override
    public Saldo haben(final MonetaryAmount geldbetrag)
    {
        if (this.betrag.isEqualTo(geldbetrag))
        {
            return new SollHabenSaldo();
        }

        if (this.betrag.isLessThan(geldbetrag))
        {
            return new Habensaldo(geldbetrag.subtract(this.betrag));
        }

        return new Sollsaldo(this.betrag.subtract(geldbetrag));
    }

    @Override
    public String toString()
    {
        final MonetaryAmountFormat format = MonetaryFormats.getAmountFormat(Locale.GERMANY);
        final String betrag = format.format(this.getBetrag());

        return "Sollsaldo{" + betrag + "}";
    }
}
