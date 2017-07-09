package com.github.haschi.haushaltsbuch;

import cucumber.api.java.de.Dann;
import cucumber.api.java.de.Wenn;

public class HaushaltsbuchführungSchrittDefinitionen
{
    private final AbstractAutomationApi api;

    public HaushaltsbuchführungSchrittDefinitionen(final AbstractAutomationApi api) {
        this.api = api;
    }

    @Wenn("^ich mit der Haushaltsbuchführung beginne$")
    public void ichMitDerHaushaltsbuchführungBeginne() throws Throwable
    {
        api.haushaltsbuchführung(AbstractHaushaltsbuchführungSteps::beginnen);
    }

    @Dann("^werde ich ein Hauptbuch mit Konten des Standardkontenrahmen angelegt haben$")
    public void werdeIchEinHauptbuchMitKontenDesStandardKontenrahmenAngelegtHaben() throws Throwable
    {
        api.haushaltsbuchführung(h -> h.hauptbuchAngelegt(
                h.aktuellesHaushaltsbuch(),
                h.aktuellesHauptbuch()));
    }
}
