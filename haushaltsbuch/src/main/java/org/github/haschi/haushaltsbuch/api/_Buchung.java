package org.github.haschi.haushaltsbuch.api;

import org.apache.commons.lang3.StringUtils;
import org.github.haschi.haushaltsbuch.infrastruktur.modellierung.de.Information;

@Information
public interface _Buchung
{
    String buchungstext();

    Währungsbetrag betrag();

    static Buchung leer()
    {
        return Buchung.builder()
                .buchungstext(StringUtils.EMPTY)
                .betrag(_Währungsbetrag.NullEuro())
                .build();
    }
}
