package de.therapeutenkiller.haushaltsbuch.domaene;

import org.apache.commons.lang3.ObjectUtils;
import org.javamoney.moneta.Money;

import javax.money.Monetary;
import javax.money.MonetaryAmountFactory;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by mhaschka on 10.10.15.
 */
public class Haushaltsbuch {

  private Set<Konto> konten = new HashSet<>();

  public Money gesamtvermögenBerechnen() {

    MonetaryAmountFactory<Money> fact = Monetary.getAmountFactory(Money.class);
    Money gesamtvermögen = fact.setCurrency("EUR").setNumber(0).create();

    for (Konto konto : this.konten) {
      gesamtvermögen = gesamtvermögen.add(konto.bestandBerechnen());
    }

    return gesamtvermögen;
  }

  public void neuesKontoHinzufügen(Konto konto) {
    this.konten.add(konto);
  }
}
