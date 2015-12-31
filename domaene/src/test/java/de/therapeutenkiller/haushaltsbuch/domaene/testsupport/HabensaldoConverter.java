package de.therapeutenkiller.haushaltsbuch.domaene.testsupport;

import cucumber.api.Transformer;
import de.therapeutenkiller.haushaltsbuch.domaene.aggregat.Habensaldo;

public final class HabensaldoConverter extends Transformer<Habensaldo> {

    @Override
    public Habensaldo transform(final String währungsbetrag) {
        final MoneyConverter converter = new MoneyConverter();
        return new Habensaldo(converter.transform(währungsbetrag));
    }
}
