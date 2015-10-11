package de.therapeutenkiller.haushaltsbuch.domaene;

import org.javamoney.moneta.Money;

import javax.money.Monetary;
import javax.money.MonetaryAmountFactory;
import java.util.HashSet;
import java.util.Set;

class Haushaltsbuch {

  private final Set<Konto> konten = new HashSet<>();

  public Money gesamtvermögenBerechnen() {

    final MonetaryAmountFactory<Money> fact = Monetary.getAmountFactory(Money.class);
    Money gesamtvermögen = fact.setCurrency("EUR").setNumber(0).create();

    for (final Konto konto : this.konten) {
      gesamtvermögen = gesamtvermögen.add(konto.bestandBerechnen());
    }

    return gesamtvermögen;
  }

  public void neuesKontoHinzufügen(final Konto konto) {
    this.konten.add(konto);
  }
}
