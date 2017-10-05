package org.github.haschi.haushaltsbuch.api;

import org.github.haschi.haushaltsbuch.infrastruktur.modellierung.de.Information;
import org.immutables.value.Value;

@Information
public abstract class _Inventar
{
    public static Inventar leer()
    {
        return Inventar.builder()
                .anlagevermögen(Vermögenswerte.leer())
                .umlaufvermögen(Vermögenswerte.leer())
                .schulden(Schulden.leer())
                .build();
    }

    public abstract Vermögenswerte anlagevermögen();

    public abstract Vermögenswerte umlaufvermögen();

    public abstract Schulden schulden();

    @Value.Derived
    public Reinvermögen reinvermögen()
    {
        final Währungsbetrag anlagevermögen = anlagevermögen().summe();
        final Währungsbetrag umlaufvermögen = umlaufvermögen().summe();
        final Währungsbetrag schulden = schulden().summe();

        return Reinvermögen.builder()
                .summeDesVermögens(Währungsbetrag.of(anlagevermögen.wert().add(umlaufvermögen.wert())))
                .summeDerSchulden(schulden)
                .build();
    }
}
