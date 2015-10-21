package de.therapeutenkiller.haushaltsbuch.domaene;

import org.javamoney.moneta.Money;
import org.javamoney.moneta.function.MonetaryFunctions;

import javax.money.CurrencyUnit;
import javax.money.Monetary;
import javax.money.MonetaryAmount;
import javax.money.MonetaryAmountFactory;
import java.util.HashSet;
import java.util.Locale;
import java.util.Optional;
import java.util.Set;

@CoverageIgnore
class Haushaltsbuch {

  private final Set<Konto> konten = new HashSet<>();
  private final Set<Buchungssatz> buchungssätze = new HashSet<>();

  public Haushaltsbuch() {
    final CurrencyUnit euro = Monetary.getCurrency(Locale.GERMANY);
    final Money anfang = Money.of(0, euro);
    this.konten.add(new Konto("Anfangsbestand", anfang));
  }

  public Money gesamtvermögenBerechnen() {

    final MonetaryAmountFactory<Money> fact = Monetary.getAmountFactory(Money.class);
    Money gesamtvermögen = fact.setCurrency("EUR").setNumber(0).create();

    for (final Konto konto : this.konten) {
      gesamtvermögen = gesamtvermögen.add(konto.bestandBerechnen());
    }

    return gesamtvermögen;
  }

  public void neuesKontoHinzufügen(final Konto konto, final Money anfangsbestand) {
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
        .filter(k -> k.getBezeichnung() == kontoname)
        .findFirst()
        .get();
  }

  @CoverageIgnore
  public MonetaryAmount kontostandBerechnen(final Konto einKonto) {
    final Optional<MonetaryAmount> plus = this.buchungssätze.stream()
        .filter(bs -> bs.getHabenkonto().getBezeichnung() == einKonto.getBezeichnung())
        .map(bs -> (MonetaryAmount) bs.getWährungsbetrag())
            .reduce(MonetaryFunctions.sum());

    return plus.get();
  }
}
