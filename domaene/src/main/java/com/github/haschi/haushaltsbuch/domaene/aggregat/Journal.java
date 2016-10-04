package com.github.haschi.haushaltsbuch.domaene.aggregat;

import com.github.haschi.dominium.aggregat.Spezifikation;
import com.google.common.collect.ImmutableList;
import org.javamoney.moneta.Money;
import org.javamoney.moneta.function.MonetaryFunctions;

import javax.money.Monetary;
import javax.money.MonetaryAmount;
import java.util.HashSet;
import java.util.Locale;
import java.util.Set;

public final class Journal
{
    public static final Journal UNDEFINIERT = new Journal();
    public final Set<Buchungssatz> buchungssätze = new HashSet<>();

    public Journal()
    {
        super();
    }

    public boolean istAnfangsbestandFürKontoVorhanden(final Konto konto)
    {
        return this.buchungssätze.stream().anyMatch(buchungssatz -> buchungssatz.istAnfangsbestandFür(konto.getName()));
    }

    void buchungssatzHinzufügen(final Buchungssatz buchungssatz)
    {
        this.buchungssätze.add(buchungssatz);
    }

    MonetaryAmount summeFür(final Spezifikation<Buchungssatz> buchungssatzSpezifikation)
    {
        return this.buchungssätze.stream()
                .filter(buchungssatzSpezifikation::istErfülltVon)
                .map(Buchungssatz::getWährungsbetrag)
                .reduce(MonetaryFunctions.sum())
                .orElse(Money.of(0, Monetary.getCurrency(Locale.GERMANY)));
    }

    ImmutableList<Set<Buchungssatz>> getBuchungssätze()
    {
        return ImmutableList.of(this.buchungssätze);
    }
}