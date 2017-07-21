package com.github.haschi.haushaltsbuch;

import cucumber.api.java.de.Dann;

import java.util.List;

public class KontenrahmenAnlegenSchrittDefinitionen
{
    private final AbstractAutomationApi api;

    public KontenrahmenAnlegenSchrittDefinitionen(final AbstractAutomationApi api)
    {
        this.api = api;
    }

    @Dann("^werde ich einen Kontenrahmen mit folgenden Konten für mein Hauptbuch angelegt haben:$")
    public void werdeIchEinenKontenrahmenMitFolgendenKontenFürMeinHauptbuchAngelegtHaben(
            final List<Kontendefinition> konten)
    {
        api.haushaltsbuchführung(h -> h.hauptbuch(hauptbuch -> hauptbuch.kontenrahmenAngelegt(konten)));
    }
}
