package org.github.haschi.haushaltsbuch.api;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonUnwrapped;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import org.apache.commons.lang3.StringUtils;
import org.github.haschi.haushaltsbuch.infrastruktur.modellierung.de.Information;
import org.immutables.builder.Builder;
import org.immutables.value.Value;
import org.javamoney.moneta.Money;

import javax.money.Monetary;
import javax.money.MonetaryAmount;
import javax.money.format.MonetaryAmountFormat;
import javax.money.format.MonetaryFormats;
import java.util.Locale;

@JsonDeserialize(builder = WährungsbetragBuilder.class)
@JsonIgnoreProperties({"wert"})
@Information
public abstract class _Währungsbetrag
{
    @JsonIgnore
    @Value.Parameter
    public abstract MonetaryAmount wert();


    @Builder.Factory
    public static Währungsbetrag währungsbetrag(final @JsonProperty(value = "betrag") String betrag)
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
    @JsonProperty(value = "betrag")
    @JsonUnwrapped
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
