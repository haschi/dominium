package org.github.haschi.haushaltsbuch.api;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
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

@JsonDeserialize(using = WährungsbetragDeserialisierer.class, as = Währungsbetrag.class)
@JsonSerialize(using = WährungsbetragSerialisierer.class)
@Eingehüllt
@Value.Immutable
public abstract class _Währungsbetrag extends Umhüller<MonetaryAmount>
{
    public static Währungsbetrag währungsbetrag(final String betrag)
    {
        if (StringUtils.isEmpty(betrag))
        {
            throw new IllegalArgumentException("Währungsbetrag ist leer");
        }

        final DeutschenWährungsbetragAnalysieren analysieren = new DeutschenWährungsbetragAnalysieren();
        final MonetaryAmount s = analysieren.aus(betrag);

        return Währungsbetrag.of(s);
    }

    public static Währungsbetrag NullEuro()
    {
        return Währungsbetrag.of(
                Money.of(0, Monetary.getCurrency(Locale.GERMANY)));
    }

    @Override
    // @JsonProperty("betrag")
    public final String toString()
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
