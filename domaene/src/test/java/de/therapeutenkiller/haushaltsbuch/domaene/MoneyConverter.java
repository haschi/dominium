package de.therapeutenkiller.haushaltsbuch.domaene;

import cucumber.api.Transformer;
import org.apache.commons.lang3.StringUtils;

import javax.money.MonetaryAmount;

public class MoneyConverter extends Transformer<MonetaryAmount> {

    @Override
    public final MonetaryAmount transform(final String währungsbetrag) {
        if (StringUtils.isEmpty(währungsbetrag)) {
            throw new IllegalArgumentException("Währungsbetrag ist leer");
        }

        final WährungsbetragParsen parsen = new DeutschenWährungsbetragParsen();
        return parsen.aus(währungsbetrag);
    }
}
