package de.therapeutenkiller.haushaltsbuch.domaene;

import org.javamoney.moneta.Money;

/**
 * Created by mhaschka on 10.10.15.
 */
public class Konto {

  private String kontoname;
  private Money anfangsbestand;

  public Konto(String kontoname, Money anfangsbestand) {

    this.kontoname = kontoname;
    this.anfangsbestand = anfangsbestand;
  }

  public Money bestandBerechnen() {
    return anfangsbestand;
  }
}
