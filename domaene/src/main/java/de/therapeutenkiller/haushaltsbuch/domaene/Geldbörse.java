package de.therapeutenkiller.haushaltsbuch.domaene;

import org.javamoney.moneta.Money;

public final class Geldbörse {

    private Money geld;

    Geldbörse(final Money einGeld) {
        this.geld = einGeld;
    }

    public static Geldbörse erzeugen() {
        return new Geldbörse(Money.of(0, "EUR"));
    }

    public void hineinstecken(final Money einGeld) {
        this.geld = this.geld.add(einGeld);
    }

    public Money getInhalt() {
        return this.geld;
    }
}
