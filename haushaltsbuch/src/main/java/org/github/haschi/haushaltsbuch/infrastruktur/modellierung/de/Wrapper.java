package org.github.haschi.haushaltsbuch.infrastruktur.modellierung.de;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import org.immutables.value.Value;


@JsonSerialize(using = EinfacherWertSerialisierer.class)
public abstract class Wrapper<T>
{
    @Value.Parameter
    @JsonProperty
    public abstract T wert();

    @Override
    public String toString()
    {
        return getClass().getSimpleName() + "(" + wert() + ")";
    }
}