package com.github.haschi.haushaltsbuch.api;

import org.javamoney.moneta.Money;

import javax.money.Monetary;
import javax.money.MonetaryAmount;
import javax.money.format.MonetaryAmountFormat;
import javax.money.format.MonetaryFormats;
import java.math.BigDecimal;
import java.util.Locale;

/**
 * Die Klasse SollHabenSaldo ist sowohl Sollsaldo wie auch Habensaldo. Dies ist immer
 * der Fall, wenn der Saldo 0.00 EUR beträgt. Es kann dann nicht entschieden werden,
 * ob es sich um einen Soll- oder Habensaldo handelt.
 */
public final class SollHabenSaldo
        extends Saldo
{

    public SollHabenSaldo()
    {
        super(Money.of(BigDecimal.ZERO, Monetary.getCurrency("EUR")));
    }

    @Override
    public final MonetaryAmount getBetrag()
    {
        return this.betrag;
    }

    @Override
    public final Saldo soll(final MonetaryAmount geldbetrag)
    {
        return new Sollsaldo(geldbetrag);
    }

    @Override
    public final Saldo haben(final MonetaryAmount geldbetrag)
    {
        return new Habensaldo(geldbetrag);
    }

    @Override
    public final String toString()
    {
        final MonetaryAmountFormat format = MonetaryFormats.getAmountFormat(Locale.GERMANY);
        final String betrag = format.format(this.getBetrag());

        return "Soll- Habensaldo{" + betrag + "}";
    }
}
