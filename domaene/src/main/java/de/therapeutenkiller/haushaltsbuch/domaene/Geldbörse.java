package de.therapeutenkiller.haushaltsbuch.domaene;

import org.javamoney.moneta.Money;

public final class Geldbörse {

  public static Geldbörse erzeugen() {
    return new Geldbörse(Money.of(0, "EUR"));
  }

  private Money geld;

  private Geldbörse(final Money einGeld) {
    this.geld = einGeld;
  }

  public void hineinstecken(final Money einGeld) {
    this.geld = this.geld.add(einGeld);
  }

  public Money getInhalt() {
    return this.geld;
  }
}
