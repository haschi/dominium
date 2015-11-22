package de.therapeutenkiller.haushaltsbuch.domaene;

import org.javamoney.moneta.Money;
import org.javamoney.moneta.function.MonetaryFunctions;

import javax.money.CurrencyUnit;
import javax.money.Monetary;
import javax.money.MonetaryAmount;
import java.util.HashSet;
import java.util.Locale;
import java.util.Set;
import java.util.UUID;

@CoverageIgnore public final class Haushaltsbuch {

    private final UUID haushaltsbuchId;
    private final Set<Konto> konten = new HashSet<>();
    private final Set<Buchungssatz> buchungssätze = new HashSet<>();

    public Haushaltsbuch() {
        this.haushaltsbuchId = UUID.randomUUID();
        final CurrencyUnit euro = Monetary.getCurrency(Locale.GERMANY);
        final Money anfang = Money.of(0, euro);
        this.konten.add(new Konto("Anfangsbestand", anfang));
    }

    public MonetaryAmount kontostandBerechnen(final String kontoname) {
        final Konto konto = this.kontoSuchen(kontoname);
        return this.kontostandBerechnen(konto);
    }

    @CoverageIgnore
    public MonetaryAmount kontostandBerechnen(final Konto einKonto) {
        return this.buchungssätze.stream()
            .filter(buchungssatz -> buchungssatz.getHabenkonto() == einKonto)
            .map(Buchungssatz::getWährungsbetrag)
            .reduce(MonetaryFunctions.sum())
            .get();
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
            .filter(konto -> new KontonameSpezifikation(kontoname).istErfülltVon(konto))
            .findFirst()
            .get();
    }

    public UUID getHaushaltsbuchId() {
        return this.haushaltsbuchId;
    }
}
