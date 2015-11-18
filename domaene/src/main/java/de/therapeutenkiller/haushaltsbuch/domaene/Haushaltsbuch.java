package de.therapeutenkiller.haushaltsbuch.domaene;

import org.javamoney.moneta.Money;
import org.javamoney.moneta.function.MonetaryFunctions;

import javax.money.CurrencyUnit;
import javax.money.Monetary;
import javax.money.MonetaryAmount;
import java.util.HashSet;
import java.util.Locale;
import java.util.Set;

@CoverageIgnore class Haushaltsbuch {

    private final Set<Konto> konten = new HashSet<>();
    private final Set<Buchungssatz> buchungssätze = new HashSet<>();

    public Haushaltsbuch() {
        final CurrencyUnit euro = Monetary.getCurrency(Locale.GERMANY);
        final Money anfang = Money.of(0, euro);
        this.konten.add(new Konto("Anfangsbestand", anfang));
    }

    public MonetaryAmount gesamtvermögenBerechnen() {

        return this.konten.stream()
            .map(Konto::bestandBerechnen)
            .reduce(MonetaryFunctions.sum())
            .get();
    }

    public void neuesKontoHinzufügen(final Konto konto, final MonetaryAmount anfangsbestand) {
        this.konten.add(konto);

        final Buchungssatz buchungssatz = new Buchungssatz(
            this.kontoSuchen("Anfangsbestand"),
            konto,
            anfangsbestand);

        this.buchungHinzufügen(buchungssatz);
    }

    private void buchungHinzufügen(final Buchungssatz buchungssatz) {
        this.buchungssätze.add(buchungssatz);
    }

    @CoverageIgnore
    public Konto kontoSuchen(final String kontoname) {
        return this.konten.stream()
            .filter(konto -> konto.getBezeichnung().equals(kontoname))
            .findFirst()
            .get();
    }

    @CoverageIgnore
    public MonetaryAmount kontostandBerechnen(final Konto einKonto) {
        return this.buchungssätze.stream()
            .filter(buchungssatz -> buchungssatz.getHabenkonto() == einKonto)
            .map(Buchungssatz::getWährungsbetrag)
            .reduce(MonetaryFunctions.sum())
            .get();
    }
}
