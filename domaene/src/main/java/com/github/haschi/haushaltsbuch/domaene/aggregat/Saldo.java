package com.github.haschi.haushaltsbuch.domaene.aggregat;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;

import javax.money.MonetaryAmount;

public abstract class Saldo
{

    final MonetaryAmount betrag;

    Saldo(final MonetaryAmount betrag)
    {
        super();
        this.betrag = betrag;
    }

    public abstract MonetaryAmount getBetrag();

    @Override
    public final boolean equals(final Object object)
    {
        if (this == object)
        {
            return true;
        }

        if (!(object instanceof Saldo))
        {
            return false;
        }

        final Saldo saldo = (Saldo) object;

        return new EqualsBuilder().append(this.betrag, saldo.betrag).isEquals();
    }

    @Override
    public final int hashCode()
    {
        return new HashCodeBuilder(17, 37).append(this.betrag).toHashCode();
    }

    public abstract Saldo soll(final MonetaryAmount geldbetrag);

    public abstract Saldo haben(final MonetaryAmount geldbetrag);
}
