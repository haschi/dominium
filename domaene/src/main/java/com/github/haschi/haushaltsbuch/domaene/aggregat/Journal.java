package com.github.haschi.haushaltsbuch.domaene.aggregat;

import java.util.HashSet;
import java.util.Set;

final class Journal
{
    private final Set<Buchungssatz> buchungssätze = new HashSet<>();

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
}