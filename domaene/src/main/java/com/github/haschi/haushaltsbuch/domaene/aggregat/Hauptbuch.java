package com.github.haschi.haushaltsbuch.domaene.aggregat;

import com.github.haschi.haushaltsbuch.api.Buchungssatz;
import com.github.haschi.haushaltsbuch.api.Konto;
import com.github.haschi.haushaltsbuch.api.Kontobezeichnung;
import com.github.haschi.haushaltsbuch.api.SaldoWurdeGeändert;
import com.github.haschi.haushaltsbuch.domaene.aggregat.konto.KontoUnbekannt;
import com.github.haschi.haushaltsbuch.domaene.aggregat.konto.KontobezeichnungSpezifikation;
import javaslang.collection.HashSet;
import javaslang.collection.Set;

final class Hauptbuch
{
    private Set<Konto> konten = HashSet.empty();

    void saldoÄndern(final SaldoWurdeGeändert saldoGeaendert)
    {
        final Konto konto = this.suchen(Kontobezeichnung.of(saldoGeaendert.kontobezeichnung()));
        konto.setSaldo(saldoGeaendert.neuerSaldo());
    }

    boolean sindAlleKontenVorhanden(final Buchungssatz buchungssatz)
    {
        return this.sindAlleKontenVorhanden(buchungssatz.getSollkonto(), buchungssatz.getHabenkonto());
    }

    private boolean sindAlleKontenVorhanden(final Kontobezeichnung sollkonto, final Kontobezeichnung habenkonto)
    {
        return this.istKontoVorhanden(habenkonto) && this.istKontoVorhanden(sollkonto);
    }

    String fehlermeldungFürFehlendeKontenErzeugen(
            final Kontobezeichnung soll, final Kontobezeichnung haben)
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

    boolean istKontoVorhanden(final Kontobezeichnung konto)
    {
        final KontobezeichnungSpezifikation kontobezeichnung = new KontobezeichnungSpezifikation(konto);
        return this.konten.exists(kontobezeichnung::istErfülltVon);
    }

    public Konto suchen(final Kontobezeichnung kontobezeichnung)
    {
        final KontobezeichnungSpezifikation k = new KontobezeichnungSpezifikation(kontobezeichnung);
        return this.konten.find(k::istErfülltVon)
                .getOrElseThrow(() -> new KontoUnbekannt(kontobezeichnung));
    }

    public void hinzufügen(final Konto konto)
    {
        this.konten = this.konten.add(konto);
    }
}
