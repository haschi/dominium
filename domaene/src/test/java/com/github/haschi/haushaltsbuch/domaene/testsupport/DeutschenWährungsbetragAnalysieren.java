package com.github.haschi.haushaltsbuch.domaene.testsupport;

import javax.money.MonetaryAmount;
import javax.money.format.MonetaryAmountFormat;
import javax.money.format.MonetaryFormats;
import java.util.Locale;

class DeutschenWährungsbetragAnalysieren {

    private final MonetaryAmountFormat format;

    DeutschenWährungsbetragAnalysieren() {
        super();
        this.format = MonetaryFormats.getAmountFormat(Locale.GERMANY);
    }

    public final MonetaryAmount aus(final String währungsbetrag) {
        return this.format.parse(währungsbetrag);
    }
}
