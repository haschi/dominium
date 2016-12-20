package com.github.haschi.haushaltsbuch.domaene.aggregat;

import com.github.haschi.haushaltsbuch.api.Kontobezeichnung;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import javax.money.MonetaryAmount;
import javax.money.format.MonetaryAmountFormat;
import javax.money.format.MonetaryFormats;
import java.util.Locale;

public final class Buchungssatz
{

    private final Kontobezeichnung sollkonto;
    private final Kontobezeichnung habenkonto;
    private final MonetaryAmount währungsbetrag;

    public Buchungssatz(
            final Kontobezeichnung sollkonto,
            final Kontobezeichnung habenkonto,
            final MonetaryAmount währungsbetrag)
    {
        super();

        if (währungsbetrag.isNegative())
        {
            throw new IllegalArgumentException("Buchungssätze dürfen keine negativen Beträge besitzen.");
        }

        this.sollkonto = sollkonto;
        this.habenkonto = habenkonto;
        this.währungsbetrag = währungsbetrag;
    }

    public boolean hatSollkonto(final Kontobezeichnung konto)
    {
        return this.sollkonto.equals(konto);
    }

    public boolean hatHabenkonto(final Kontobezeichnung konto)
    {
        return this.habenkonto.equals(konto);
    }

    public Kontobezeichnung getSollkonto()
    {
        return this.sollkonto;
    }

    public Kontobezeichnung getHabenkonto()
    {
        return this.habenkonto;
    }

    public MonetaryAmount getWährungsbetrag()
    {
        return this.währungsbetrag;
    }

    public boolean istAnfangsbestandFür(final Kontobezeichnung konto)
    {
        return (this.habenkonto.equals(konto) && this.sollkonto.equals(Konto.ANFANGSBESTAND.getName())) || (this
                .habenkonto
                .equals(Konto.ANFANGSBESTAND.getName()) && this.sollkonto.equals(konto));
    }

    @Override
    public String toString()
    {
        final MonetaryAmountFormat format = MonetaryFormats.getAmountFormat(Locale.GERMANY);
        final String betrag = format.format(this.währungsbetrag);

        return String.format("%s (%s) an %s (%s)",
                             this.sollkonto.toString(), betrag, this.habenkonto.toString(), betrag);
    }

    @Override
    public boolean equals(final Object o)
    {
        if (this == o)
        {
            return true;
        }

        if (o == null || getClass() != o.getClass())
        {
            return false;
        }

        final Buchungssatz that = (Buchungssatz) o;

        return new EqualsBuilder().append(this.sollkonto, that.sollkonto)
                .append(this.habenkonto, that.habenkonto)
                .append(this.währungsbetrag, that.währungsbetrag)
                .isEquals();
    }

    @Override
    public int hashCode()
    {
        return new HashCodeBuilder(17, 37).append(this.sollkonto)
                .append(this.habenkonto)
                .append(this.währungsbetrag)
                .toHashCode();
    }
}
