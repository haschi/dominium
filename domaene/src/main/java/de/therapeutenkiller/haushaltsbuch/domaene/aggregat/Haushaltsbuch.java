package de.therapeutenkiller.haushaltsbuch.domaene.aggregat;

import de.therapeutenkiller.haushaltsbuch.domaene.CoverageIgnore;
import de.therapeutenkiller.haushaltsbuch.domaene.KontonameSpezifikation;
import de.therapeutenkiller.haushaltsbuch.domaene.SollkontoSpezifikation;
import de.therapeutenkiller.haushaltsbuch.domaene.support.Entität;
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

        this.konten.add(new Konto("Anfangsbestand"));
    }

    public MonetaryAmount kontostandBerechnen(final String kontoname) {
        final Konto konto = this.kontoSuchen(kontoname);
        return this.kontostandBerechnen(konto);
    }

    @CoverageIgnore
    public MonetaryAmount kontostandBerechnen(final Konto einKonto) {

        final SollkontoSpezifikation sollkonto = new SollkontoSpezifikation(einKonto);

        return this.buchungssätze.stream()
                .filter(sollkonto::istErfülltVon)
                .map(Buchungssatz::getWährungsbetrag)
                .reduce(MonetaryFunctions.sum())
                .orElse(Money.of(0, Monetary.getCurrency(Locale.GERMANY)));
    }

    public MonetaryAmount gesamtvermögenBerechnen() {

        return this.buchungssätze.stream()
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
            final String sollkontoName,
            final String habenkontoName,
            final MonetaryAmount betrag) {
        final Konto soll = new Konto(sollkontoName);
        final Konto haben = new Konto(habenkontoName);

        this.buchungssätze.add(new Buchungssatz(soll, haben, betrag));
    }
}
