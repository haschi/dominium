package org.github.haschi.haushaltsbuch.api;

import org.apache.commons.lang3.StringUtils;
import org.github.haschi.haushaltsbuch.infrastruktur.modellierung.de.Eingehüllt;
import org.github.haschi.haushaltsbuch.infrastruktur.modellierung.de.Umhüller;
import org.immutables.value.Value;
import org.javamoney.moneta.Money;

import javax.money.Monetary;
import javax.money.MonetaryAmount;
import javax.money.format.MonetaryAmountFormat;
import javax.money.format.MonetaryFormats;
import java.util.Locale;

@Value.Immutable
// @XStreamConverter(MoneyConverter.class)
@Eingehüllt
public abstract class _Währungsbetrag extends Umhüller<MonetaryAmount>
{
    public static Währungsbetrag parse(final String s)
    {
        if (StringUtils.isEmpty(s))
        {
            throw new IllegalArgumentException("Währungsbetrag ist leer");
        }

        final DeutschenWährungsbetragAnalysieren analysieren = new DeutschenWährungsbetragAnalysieren();
        final MonetaryAmount betrag = analysieren.aus(s);

        return Währungsbetrag.of(betrag);
    }

    public static Währungsbetrag NullEuro()
    {
        return Währungsbetrag.of(
                Money.of(0, Monetary.getCurrency(Locale.GERMANY)));
    }

    @Override
    public String toString()
    {
        if (wert() != null )
        {
            final MonetaryAmountFormat deutschesFormat = MonetaryFormats.getAmountFormat(Locale.GERMANY);
            return deutschesFormat.format(wert());
        } else {
            return StringUtils.EMPTY;
        }
    }
}
