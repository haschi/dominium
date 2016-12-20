package com.github.haschi.haushaltsbuch.domaene.aggregat;

import com.github.haschi.haushaltsbuch.api.Kontoart;
import com.github.haschi.haushaltsbuch.api.Kontoname;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import javax.money.MonetaryAmount;

public final class Konto
{

    public static final Konto ANFANGSBESTAND = new Konto(
            Kontoname.of("Anfangsbestand"),
            new KeineRegel(),
            Kontoart.Aktiv);

    private final Kontoname kontoname;

    private final Buchungsregel regel;

    private final Kontoart kontoart;

    private Saldo saldo;

    public Konto(final Kontoname kontoname, final Buchungsregel regel, final Kontoart kontoart)
    {

        super();
        this.regel = regel;
        this.kontoart = kontoart;
        this.saldo = new SollHabenSaldo();

        this.kontoname = kontoname;
    }

    public static Konto aktiv(final String kontoname)
    {
        return of(kontoname, Kontoart.Aktiv);
    }

    public static Konto aufwand(final String kontoname)
    {
        return of(kontoname, Kontoart.Aufwand);
    }

    private static Konto of(final String kontoname, final Kontoart kontoart)
    {
        final Kontoname name = Kontoname.of(kontoname);
        final BuchungsregelFabrik fabrik = new BuchungsregelFabrik(kontoart);
        return new Konto(name, fabrik.erzeugen(name), kontoart);
    }

    public void setSaldo(final Saldo saldo)
    {
        this.saldo = saldo;
    }

    @Override
    public String toString()
    {
        return "Konto{" + "kontoname='" + this.kontoname + '\'' + '}';
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

    public Kontoart getKontoart()
    {
        return this.kontoart;
    }

    public Saldo buchen(final Buchungssatz buchungssatz)
    {
        if (buchungssatz.hatSollkonto(this.kontoname))
        {
            return this.saldo.soll(buchungssatz.getWährungsbetrag());
        }

        if (buchungssatz.hatHabenkonto(this.kontoname))
        {
            return this.saldo.haben(buchungssatz.getWährungsbetrag());
        }

        throw new IllegalArgumentException();
    }

    public Kontoname getName()
    {
        return this.kontoname;
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

        return new EqualsBuilder().append(this.kontoname, konto.kontoname).isEquals();
    }

    @Override
    public int hashCode()
    {
        return new HashCodeBuilder(17, 37).append(this.kontoname).toHashCode();
    }
}
