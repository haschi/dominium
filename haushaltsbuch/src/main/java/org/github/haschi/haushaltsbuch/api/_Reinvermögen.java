package org.github.haschi.haushaltsbuch.api;

import org.github.haschi.haushaltsbuch.infrastruktur.modellierung.de.Information;
import org.immutables.value.Value;

@Information
public abstract class _Reinvermögen
{
    public abstract Währungsbetrag summeDesVermögens();

    public abstract Währungsbetrag summeDerSchulden();

    @Value.Derived
    public Währungsbetrag reinvermögen()
    {
        return Währungsbetrag.of(
                summeDesVermögens().wert()
                        .subtract(summeDerSchulden().wert()));
    }
}
