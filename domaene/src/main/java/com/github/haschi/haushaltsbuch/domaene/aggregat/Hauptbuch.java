package com.github.haschi.haushaltsbuch.domaene.aggregat;

import com.github.haschi.haushaltsbuch.domaene.KontonameSpezifikation;
import com.github.haschi.haushaltsbuch.domaene.aggregat.ereignis.SaldoWurdeGeaendert;
import com.google.common.collect.ImmutableSet;

import java.util.HashSet;
import java.util.Set;

public final class Hauptbuch {

    public static final Hauptbuch UNDEFINIERT = new Hauptbuch();
    public final Set<Konto> konten = new HashSet<>();

    void saldoÄndern(final SaldoWurdeGeaendert saldoGeaendert) {
        final Konto konto = this.suchen(saldoGeaendert.kontoname());
        konto.setSaldo(saldoGeaendert.neuerSaldo());
    }

    boolean sindAlleBuchungskontenVorhanden(final Buchungssatz buchungssatz) {
        return this.sindAlleBuchungskontenVorhanden(buchungssatz.getSollkonto(), buchungssatz.getHabenkonto());
    }

    boolean sindAlleBuchungskontenVorhanden(final String sollkonto, final String habenkonto) {
        return this.istKontoVorhanden(habenkonto) && this.istKontoVorhanden(sollkonto);
    }

    String fehlermeldungFürFehlendeKontenErzeugen(
            final String soll,
            final String haben) {

        if (!this.istKontoVorhanden(soll) && this.istKontoVorhanden(haben)) {
            return String.format("Das Konto %s existiert nicht.", soll);
        }

        if (this.istKontoVorhanden(soll) && !this.istKontoVorhanden(haben)) {
            return String.format("Das Konto %s existiert nicht.", haben);
        }

        if (!this.istKontoVorhanden(soll) && !this.istKontoVorhanden(haben)) {

            return String.format("Die Konten %s und %s existieren nicht.", soll, haben);
        }

        throw new IllegalArgumentException("Die Fehlermeldung kann nicht erzeugt werden, da kein Fehler vorliegt.");
    }

    boolean kannAusgabeGebuchtWerden(final Buchungssatz buchungssatz) {
        final Konto sollkonto = this.suchen(buchungssatz.getSollkonto());
        final Konto habenkonto = this.suchen(buchungssatz.getHabenkonto());

        return sollkonto.kannAusgabeBuchen(buchungssatz)
                && habenkonto.kannAusgabeBuchen(buchungssatz);
    }

    public boolean kannTilgungGebuchtWerden(final Buchungssatz buchungssatz) {
        final Konto sollkonto = this.suchen(buchungssatz.getSollkonto());
        final Konto habenkonto = this.suchen(buchungssatz.getHabenkonto());

        return sollkonto.kannTilgungGebuchtWerden(buchungssatz)
                && habenkonto.kannTilgungGebuchtWerden(buchungssatz);
    }

    boolean istKontoVorhanden(final String konto) {
        final KontonameSpezifikation kontoname = new KontonameSpezifikation(konto);
        return this.getKonten().stream().anyMatch(kontoname::istErfülltVon);
    }

    public ImmutableSet<Konto> getKonten() {
        return ImmutableSet.copyOf(this.konten);
    }

    public Konto suchen(final String kontoname) {
        final KontonameSpezifikation kontonameSpezifikation = new KontonameSpezifikation(kontoname);

        return this.getKonten().stream()
                .filter(kontonameSpezifikation::istErfülltVon)
                .findFirst()
                .get();
    }

    public void hinzufügen(final Konto konto) {
        this.konten.add(konto);
    }
}
