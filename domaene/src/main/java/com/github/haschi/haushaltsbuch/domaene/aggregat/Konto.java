package com.github.haschi.haushaltsbuch.domaene.aggregat;

import com.github.haschi.haushaltsbuch.api.Kontobezeichnung;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;

import javax.money.MonetaryAmount;

public final class Konto
{

    public static final Konto ANFANGSBESTAND = new Konto(
            Kontobezeichnung.of("Anfangsbestand"),
            new KeineRegel()
    );

    private final Kontobezeichnung bezeichnung;

    private final Buchungsregel regel;

    private Saldo saldo;

    public Konto(final Kontobezeichnung bezeichnung, final Buchungsregel regel)
    {

        super();
        this.regel = regel;
        this.saldo = new SollHabenSaldo();

        this.bezeichnung = bezeichnung;
    }

    public void setSaldo(final Saldo saldo)
    {
        this.saldo = saldo;
    }

    @Override
    public String toString()
    {
        return "Konto{" + "bezeichnung='" + this.bezeichnung + '\'' + '}';
    }

    @SuppressWarnings("checkstyle")
    public boolean kannAusgabeBuchen(final Buchungssatz buchungssatz)
    {
        return this.regel.kannErtragBuchen(buchungssatz);
    }

    public boolean kannTilgungGebuchtWerden(final Buchungssatz buchungssatz)
    {
        return this.regel.kannVerlustBuchen(buchungssatz);
    }

    public Buchungssatz buchungssatzFürAnfangsbestand(final MonetaryAmount betrag)
    {
        return this.regel.buchungssatzFürAnfangsbestand(this, betrag);
    }

    public Saldo buchen(final Buchungssatz buchungssatz)
    {
        if (buchungssatz.hatSollkonto(this.bezeichnung))
        {
            return this.saldo.soll(buchungssatz.getGeldbetrag());
        }

        if (buchungssatz.hatHabenkonto(this.bezeichnung))
        {
            return this.saldo.haben(buchungssatz.getGeldbetrag());
        }

        throw new IllegalArgumentException();
    }

    public Kontobezeichnung getName()
    {
        return this.bezeichnung;
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

        final Konto konto = (Konto) o;

        return new EqualsBuilder().append(this.bezeichnung, konto.bezeichnung).isEquals();
    }

    @Override
    public int hashCode()
    {
        return new HashCodeBuilder(17, 37).append(this.bezeichnung).toHashCode();
    }
}
