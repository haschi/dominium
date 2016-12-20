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
    public Saldo soll(final MonetaryAmount geldbetrag)
    {
        if (this.betrag.isEqualTo(geldbetrag))
        {
            return new SollHabenSaldo();
        }

        if (this.betrag.isGreaterThan(geldbetrag))
        {
            return new Habensaldo(this.betrag.subtract(geldbetrag));
        }

        return new Sollsaldo(geldbetrag.subtract(this.betrag));
    }

    @Override
    public Saldo haben(final MonetaryAmount geldbetrag)
    {
        return new Habensaldo(this.betrag.add(geldbetrag));
    }

    @Override
    public String toString()
    {
        final MonetaryAmountFormat format = MonetaryFormats.getAmountFormat(Locale.GERMANY);
        final String betrag = format.format(this.getBetrag());
        return "Habensaldo{" + betrag + "}";
    }
}
