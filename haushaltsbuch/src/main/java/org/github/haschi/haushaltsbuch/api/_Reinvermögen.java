package org.github.haschi.haushaltsbuch.api;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonUnwrapped;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import org.github.haschi.haushaltsbuch.infrastruktur.modellierung.de.Information;
import org.immutables.value.Value;

@Information
@JsonSerialize(as = Reinvermögen.class)
@JsonDeserialize(as = Reinvermögen.class)
public abstract class _Reinvermögen
{
    @JsonProperty(value = "summeDesVermoegens")
    public abstract Währungsbetrag summeDesVermögens();

    @JsonProperty(value = "summeDerSchulden")
    public abstract Währungsbetrag summeDerSchulden();

    @Value.Derived
    public Währungsbetrag reinvermögen()
    {
        return Währungsbetrag.of(
                summeDesVermögens().wert()
                        .subtract(summeDerSchulden().wert()));
    }
}
