package de.therapeutenkiller.haushaltsbuch.domaene;

import javax.money.MonetaryAmount;
import javax.money.format.MonetaryAmountFormat;
import javax.money.format.MonetaryFormats;
import java.util.Locale;

public class DeutschenWährungsbetragParsen implements WährungsbetragParsen {

    private final MonetaryAmountFormat format;

    public DeutschenWährungsbetragParsen() {
        this.format = MonetaryFormats.getAmountFormat(Locale.GERMANY);
    }

    @Override
    public final MonetaryAmount aus(final String währungsbetrag) {
        return this.format.parse(währungsbetrag);
    }

    public String formatieren(MonetaryAmount amount) {
        return format.format(amount);
    }
}
