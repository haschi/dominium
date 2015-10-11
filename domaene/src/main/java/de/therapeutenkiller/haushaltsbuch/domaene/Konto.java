package de.therapeutenkiller.haushaltsbuch.domaene;

import org.javamoney.moneta.Money;

/**
 * Created by mhaschka on 10.10.15.
 */
class Konto {

  private String kontoname;
  private Money anfangsbestand;

  public Konto(final String kontoname, final Money anfangsbestand) {

    this.kontoname = kontoname;
    this.anfangsbestand = anfangsbestand;
  }

  public Money bestandBerechnen() {
    return this.anfangsbestand;
  }
}
