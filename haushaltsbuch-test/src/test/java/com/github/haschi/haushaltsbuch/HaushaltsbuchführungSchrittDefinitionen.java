package com.github.haschi.haushaltsbuch;

import cucumber.api.java.de.Dann;
import cucumber.api.java.de.Und;
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
        api.haushaltsbuchführung(haushaltsbuch -> haushaltsbuch.beginnen());
    }

    @Dann("^werde ich ein Hauptbuch angelegt haben$")
    public void werdeIchEinHauptbuchMitKontenDesStandardKontenrahmenAngelegtHaben() throws Throwable
    {
        api.haushaltsbuchführung(
                haushaltsbuch -> haushaltsbuch.aktuellesHaushaltsbuch(
                        aktuell -> aktuell.hauptbuch(
                                hauptbuch -> hauptbuch.angelegt())));
    }

    @Und("^ich werde ein Journal angelegt haben$")
    public void ichWerdeEinJournalZumHauptbuchAngelegtHaben() throws Throwable
    {
        api.haushaltsbuchführung(
                haushaltsbuch -> haushaltsbuch.aktuellesHaushaltsbuch(
                        aktuell -> aktuell.journal(
                                journal -> journal.angelegt())));
    }
}
