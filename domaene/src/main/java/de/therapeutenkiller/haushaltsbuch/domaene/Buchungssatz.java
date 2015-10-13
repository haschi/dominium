package de.therapeutenkiller.haushaltsbuch.domaene;

import org.javamoney.moneta.Money;

public class Buchungssatz {
  private final Konto sollkonto;
  private final Konto habenkonto;
  private final Money währungsbetrag;

  public Buchungssatz(Konto sollkonto, Konto habenkonto, Money währungsbetrag) {
    this.sollkonto = sollkonto;
    this.habenkonto = habenkonto;
    this.währungsbetrag = währungsbetrag;
  }

  public Konto getSollkonto() {
    return sollkonto;
  }

  public Konto getHabenkonto() {
    return habenkonto;
  }

  public Money getWährungsbetrag() {
    return währungsbetrag;
  }
}
