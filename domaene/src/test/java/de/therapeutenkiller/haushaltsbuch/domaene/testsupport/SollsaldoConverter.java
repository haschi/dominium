package de.therapeutenkiller.haushaltsbuch.domaene.testsupport;

import cucumber.api.Transformer;
import de.therapeutenkiller.haushaltsbuch.domaene.aggregat.Sollsaldo;

/**
 * Created by matthias on 20.12.15.
 */
public final class SollsaldoConverter extends Transformer<Sollsaldo> {

    @Override
    public Sollsaldo transform(final String währungsbetrag) {
        final MoneyConverter moneyConverter = new MoneyConverter();
        return new Sollsaldo(moneyConverter.transform(währungsbetrag));
    }
}
