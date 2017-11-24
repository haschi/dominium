package org.github.haschi.haushaltsbuch.api;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import org.github.haschi.haushaltsbuch.infrastruktur.modellierung.de.Information;
import org.immutables.value.Value;
import org.javamoney.moneta.function.MonetaryFunctions;

import java.util.List;

@Information
@JsonSerialize(as = Inventar.class)
@JsonDeserialize(as = Inventar.class)
public abstract class _Inventar
{
    public static Inventar leer()
    {
        return Inventar.builder()
                .build();
    }

    public abstract Vermögenswerte anlagevermoegen();

    public abstract Vermögenswerte umlaufvermoegen();

    public abstract Schulden schulden();

    @Value.Derived
    @JsonIgnore
    public Reinvermögen reinvermoegen()
    {
        final Währungsbetrag anlagevermögen = anlagevermoegen().summe();
        final Währungsbetrag umlaufvermögen = umlaufvermoegen().summe();
        final Währungsbetrag schulden = schulden().summe();

        return Reinvermögen.builder()
                .summeDesVermögens(Währungsbetrag.of(anlagevermögen.wert().add(umlaufvermögen.wert())))
                .summeDerSchulden(schulden)
                .build();
    }
}
