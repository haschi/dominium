package de.therapeutenkiller.haushaltsbuch.domaene.testsupport;

import cucumber.api.Transformer;

import javax.money.CurrencyUnit;
import javax.money.Monetary;

public class CurrencyUnitConverter extends Transformer<CurrencyUnit> {

    @Override public final CurrencyUnit transform(final String währung) {
        return Monetary.getCurrency(währung);
    }
}
