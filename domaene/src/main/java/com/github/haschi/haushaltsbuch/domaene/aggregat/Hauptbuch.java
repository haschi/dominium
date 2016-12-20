package com.github.haschi.haushaltsbuch.domaene.aggregat;

import com.github.haschi.haushaltsbuch.api.Kontoname;
import com.github.haschi.haushaltsbuch.api.ereignis.SaldoWurdeGeaendert;
import com.github.haschi.haushaltsbuch.domaene.aggregat.konto.KontoUnbekannt;
import com.github.haschi.haushaltsbuch.domaene.aggregat.konto.KontonameSpezifikation;
import javaslang.collection.HashSet;
import javaslang.collection.Set;

final class Hauptbuch
{
    private Set<Konto> konten = HashSet.empty();

    void saldoÄndern(final SaldoWurdeGeaendert saldoGeaendert)
    {
        final Konto konto = this.suchen(Kontoname.of(saldoGeaendert.kontoname()));
        konto.setSaldo(saldoGeaendert.neuerSaldo());
    }

    boolean sindAlleBuchungskontenVorhanden(final Buchungssatz buchungssatz)
    {
        return this.sindAlleBuchungskontenVorhanden(buchungssatz.getSollkonto(), buchungssatz.getHabenkonto());
    }

    private boolean sindAlleBuchungskontenVorhanden(final Kontoname sollkonto, final Kontoname habenkonto)
    {
        return this.istKontoVorhanden(habenkonto) && this.istKontoVorhanden(sollkonto);
    }

    String fehlermeldungFürFehlendeKontenErzeugen(
            final Kontoname soll, final Kontoname haben)
    {

        if (!this.istKontoVorhanden(soll) && this.istKontoVorhanden(haben))
        {
            return String.format("Das Konto %s existiert nicht.", soll);
        }

        if (this.istKontoVorhanden(soll) && !this.istKontoVorhanden(haben))
        {
            return String.format("Das Konto %s existiert nicht.", haben);
        }

        if (!this.istKontoVorhanden(soll) && !this.istKontoVorhanden(haben))
        {

            return String.format("Die Konten %s und %s existieren nicht.", soll, haben);
        }

        throw new IllegalArgumentException("Die Fehlermeldung kann nicht erzeugt werden, da kein Fehler vorliegt.");
    }

    boolean kannAusgabeGebuchtWerden(final Buchungssatz buchungssatz)
    {
        final Konto sollkonto = this.suchen(buchungssatz.getSollkonto());
        final Konto habenkonto = this.suchen(buchungssatz.getHabenkonto());

        return sollkonto.kannAusgabeBuchen(buchungssatz) && habenkonto.kannAusgabeBuchen(buchungssatz);
    }

    public boolean kannTilgungGebuchtWerden(final Buchungssatz buchungssatz)
    {
        final Konto sollkonto = this.suchen(buchungssatz.getSollkonto());
        final Konto habenkonto = this.suchen(buchungssatz.getHabenkonto());

        return sollkonto.kannTilgungGebuchtWerden(buchungssatz) && habenkonto.kannTilgungGebuchtWerden(buchungssatz);
    }

    boolean istKontoVorhanden(final Kontoname konto)
    {
        final KontonameSpezifikation kontoname = new KontonameSpezifikation(konto);
        return this.konten.exists(kontoname::istErfülltVon);
    }

    public Konto suchen(final Kontoname kontoname)
    {
        final KontonameSpezifikation k = new KontonameSpezifikation(kontoname);
        return this.konten.find(k::istErfülltVon)
                .getOrElseThrow(() -> new KontoUnbekannt(kontoname));
    }

    public void hinzufügen(final Konto konto)
    {
        this.konten = this.konten.add(konto);
    }
}
