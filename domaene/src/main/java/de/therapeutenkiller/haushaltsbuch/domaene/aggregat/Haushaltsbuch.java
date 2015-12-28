package de.therapeutenkiller.haushaltsbuch.domaene.aggregat;

import com.google.common.collect.ImmutableCollection;
import com.google.common.collect.ImmutableList;
import de.therapeutenkiller.haushaltsbuch.domaene.CoverageIgnore;
import de.therapeutenkiller.haushaltsbuch.domaene.HabenkontoSpezifikation;
import de.therapeutenkiller.haushaltsbuch.domaene.KontonameSpezifikation;
import de.therapeutenkiller.haushaltsbuch.domaene.SollkontoSpezifikation;
import de.therapeutenkiller.haushaltsbuch.domaene.support.Entität;
import de.therapeutenkiller.haushaltsbuch.domaene.support.Spezifikation;
import org.javamoney.moneta.Money;
import org.javamoney.moneta.function.MonetaryFunctions;

import javax.money.Monetary;
import javax.money.MonetaryAmount;
import java.util.HashSet;
import java.util.Locale;
import java.util.Set;
import java.util.UUID;

@CoverageIgnore public final class Haushaltsbuch extends Entität<UUID> {

    private final Set<Konto> konten = new HashSet<>();
    private final Set<Buchungssatz> buchungssätze = new HashSet<>();

    public Haushaltsbuch() {
        super(UUID.randomUUID());

        this.konten.add(Konto.ANFANGSBESTAND);
    }

    public String fehlermeldungFürFehlendeKontenErzeugen(
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

    public boolean sindAlleBuchungskontenVorhanden(final String sollkonto, final String habenkonto) {
        return this.istKontoVorhanden(habenkonto) && this.istKontoVorhanden(sollkonto);
    }

    public boolean sindAlleBuchungskontenVorhanden(final Buchungssatz buchungssatz) {
        return this.sindAlleBuchungskontenVorhanden(buchungssatz.getSollkonto(), buchungssatz.getHabenkonto());
    }

    public Saldo kontostandBerechnen(final String kontoname) {
        final Konto konto = this.kontoSuchen(kontoname);
        return this.kontostandBerechnen(konto);
    }

    @CoverageIgnore
    public Saldo kontostandBerechnen(final Konto konto) {

        final SollkontoSpezifikation sollkonto = new SollkontoSpezifikation(konto);
        final MonetaryAmount summerDerSollbuchungen = this.summeFür(sollkonto);

        final HabenkontoSpezifikation habenkonto = new HabenkontoSpezifikation(konto);
        final MonetaryAmount summerDerHabenbuchungen = this.summeFür(habenkonto);

        return this.saldieren(summerDerSollbuchungen, summerDerHabenbuchungen);
    }

    private Saldo saldieren(final MonetaryAmount summerDerSollbuchungen, final MonetaryAmount summerDerHabenbuchungen) {
        if (summerDerHabenbuchungen.isEqualTo(summerDerSollbuchungen)) {
            return new SollHabenSaldo(summerDerHabenbuchungen.subtract(summerDerSollbuchungen));
        }

        if (summerDerHabenbuchungen.isGreaterThan(summerDerSollbuchungen)) {
            return new Habensaldo(summerDerHabenbuchungen.subtract(summerDerSollbuchungen));
        }

        return new Sollsaldo(summerDerSollbuchungen.subtract(summerDerHabenbuchungen));
    }

    private MonetaryAmount summeFür(final Spezifikation<Buchungssatz> buchungssatzspezifikation) {
        return this.buchungssätze.stream()
                    .filter(buchungssatzspezifikation::istErfülltVon)
                    .map(Buchungssatz::getWährungsbetrag)
                    .reduce(MonetaryFunctions.sum())
                    .orElse(Money.of(0, Monetary.getCurrency(Locale.GERMANY)));
    }

    public void neuesKontoHinzufügen(final Konto konto) {
        this.konten.add(konto);
    }

    @CoverageIgnore
    public Konto kontoSuchen(final String kontoname) {

        final KontonameSpezifikation kontonameSpezifikation = new KontonameSpezifikation(kontoname);

        return this.konten.stream()
            .filter(kontonameSpezifikation::istErfülltVon)
            .findFirst()
            .get();
    }

    public void neueBuchungHinzufügen(
            final String sollkonto,
            final String habenkonto,
            final MonetaryAmount betrag) {
        this.buchungssätze.add(new Buchungssatz(sollkonto, habenkonto, betrag));
    }

    public boolean istAnfangsbestandFürKontoVorhanden(final String konto) {
        return this.buchungssätze.stream().anyMatch(buchungssatz -> buchungssatz.istAnfangsbestandFür(konto));
    }

    public boolean istKontoVorhanden(final String konto) {
        final KontonameSpezifikation kontoname = new KontonameSpezifikation(konto);
        return this.konten.stream().anyMatch(kontoname::istErfülltVon);
    }

    public ImmutableCollection<Konto> getKonten() {
        return ImmutableList.copyOf(this.konten); // NOPMD LoD TODO
    }

    public boolean kannAusgabeGebuchtWerden(final Buchungssatz buchungssatz) {
        final Konto sollkonto = this.kontoSuchen(buchungssatz.getSollkonto());
        final Konto habenkonto = this.kontoSuchen(buchungssatz.getHabenkonto());

        if (sollkonto.kannAusgabeBuchen(buchungssatz) && habenkonto.kannAusgabeBuchen(buchungssatz)) {
            return true;
        }

        return false;
    }
}
