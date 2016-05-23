package com.github.haschi.haushaltsbuch.domaene.testsupport;

import cucumber.api.Transformer;
import com.github.haschi.haushaltsbuch.domaene.aggregat.Sollsaldo;

public final class SollsaldoConverter extends Transformer<Sollsaldo> {

    @Override
    public Sollsaldo transform(final String währungsbetrag) {
        final MoneyConverter moneyConverter = new MoneyConverter();
        return new Sollsaldo(moneyConverter.transform(währungsbetrag));
    }
}
