package de.therapeutenkiller.haushaltsbuch.domaene.aggregat;

import de.therapeutenkiller.coding.aspekte.DarfNullSein;

import javax.money.MonetaryAmount;
import javax.money.format.MonetaryAmountFormat;
import javax.money.format.MonetaryFormats;
import java.util.Locale;


/** Die Klasse SollHabenSaldo ist sowohl Sollsaldo wie auch Habensaldo. Dies ist immer
 *  der Fall, wenn der Saldo 0.00 EUR beträgt. Es kann dann nicht entschieden werden,
 *  ob es sich um einen Soll- oder Habensaldo handelt.
 */
public class SollHabenSaldo extends Saldo {

    public SollHabenSaldo(MonetaryAmount betrag) {
        super(betrag);
    }

    @Override
    public String toString() {
        final MonetaryAmountFormat format = MonetaryFormats.getAmountFormat(Locale.GERMANY);
        final String betrag = format.format(this.getBetrag()); // NOPMD LoD TODO

        return "Soll- Habensaldo{"+ betrag + "}";
    }
}
