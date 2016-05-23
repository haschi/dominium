package com.github.haschi.haushaltsbuch.domaene.testsupport;

import cucumber.api.Transformer;
import org.apache.commons.lang3.StringUtils;

import javax.money.MonetaryAmount;

public class MoneyConverter extends Transformer<MonetaryAmount> {

    @Override
    public final MonetaryAmount transform(final String währungsbetrag) {
        if (StringUtils.isEmpty(währungsbetrag)) {
            throw new IllegalArgumentException("Währungsbetrag ist leer");
        }

        final DeutschenWährungsbetragAnalysieren analysieren = new DeutschenWährungsbetragAnalysieren();
        final MonetaryAmount betrag = analysieren.aus(währungsbetrag);
        if (betrag.isNegative()) { // NOPMD LoD TODO
            throw new IllegalArgumentException();
        }

        return betrag;
    }
}
