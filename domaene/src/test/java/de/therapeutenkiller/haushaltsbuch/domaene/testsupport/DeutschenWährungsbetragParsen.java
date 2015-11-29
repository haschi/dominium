package de.therapeutenkiller.haushaltsbuch.domaene.testsupport;

import javax.money.MonetaryAmount;
import javax.money.format.MonetaryAmountFormat;
import javax.money.format.MonetaryFormats;
import java.util.Locale;

public class DeutschenWährungsbetragParsen {

    private final MonetaryAmountFormat format;

    public DeutschenWährungsbetragParsen() {
        this.format = MonetaryFormats.getAmountFormat(Locale.GERMANY);
    }

    public final MonetaryAmount aus(final String währungsbetrag) {
        return this.format.parse(währungsbetrag);
    }
}
