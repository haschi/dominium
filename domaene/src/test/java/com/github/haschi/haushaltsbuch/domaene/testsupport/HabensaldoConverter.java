package com.github.haschi.haushaltsbuch.domaene.testsupport;

import cucumber.api.Transformer;
import com.github.haschi.haushaltsbuch.domaene.aggregat.Habensaldo;

public final class HabensaldoConverter extends Transformer<Habensaldo> {

    @Override
    public Habensaldo transform(final String währungsbetrag) {
        final MoneyConverter converter = new MoneyConverter();
        return new Habensaldo(converter.transform(währungsbetrag));
    }
}
