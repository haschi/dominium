package org.github.haschi.haushaltsbuch.api;

import cucumber.deps.com.thoughtworks.xstream.annotations.XStreamConverter;
import org.apache.commons.lang3.StringUtils;
import org.github.haschi.haushaltsbuch.infrastruktur.modellierung.de.Information;

@XStreamConverter(BuchungConverter.class)
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
