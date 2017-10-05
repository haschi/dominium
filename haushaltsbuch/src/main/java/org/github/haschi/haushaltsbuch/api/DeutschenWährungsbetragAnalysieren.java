package org.github.haschi.haushaltsbuch.api;

import javax.money.MonetaryAmount;
import javax.money.format.MonetaryAmountFormat;
import javax.money.format.MonetaryFormats;
import java.util.Locale;

final class DeutschenWährungsbetragAnalysieren
{

    private final MonetaryAmountFormat format;

    DeutschenWährungsbetragAnalysieren()
    {
        super();
        this.format = MonetaryFormats.getAmountFormat(Locale.GERMANY);
    }

    public MonetaryAmount aus(final String währungsbetrag)
    {
        return this.format.parse(währungsbetrag);
    }
}
