package com.github.haschi.haushaltsbuch;

import cucumber.api.java.de.Angenommen;
import cucumber.api.java.de.Dann;
import cucumber.api.java.de.Wenn;

import java.util.List;

public class EröffnungsbilanzSchrittDefinitionen
{
    AbstractAutomationApi api;

    public EröffnungsbilanzSchrittDefinitionen(final AbstractAutomationApi api) {

        this.api = api;
    }

    @Angenommen("^ich habe folgende Vermögenswerte in meinem Inventar erfasst:$")
    public void ichHabeFolgendeVermögenswerteInMeinemInventarErfasst(final List<Vermögenswert> vermögenswerte)
    {
        api.haushaltsbuchführung(haushaltsbuch ->
            haushaltsbuch.inventar(inventar ->
                inventar.anlegen(vermögenswerte)));
    }

    @Wenn("^ich die Eröffnungsbilanz aus dem Inventar erstelle$")
    public void ichDieEröffnungsbilanzAusDemInventarErstelle()
    {
        api.haushaltsbuchführung(haushaltsbuch ->
            haushaltsbuch.eröffnungsbilanz(eröffnungsbilanz ->
                eröffnungsbilanz.erstellen(haushaltsbuch.inventar())));
    }

    @Dann("^werde ich ein Eröffnungsbilanzkonto mit folgendem Inhalt erstellt haben:$")
    public void werdeIchEinEröffnungsbilanzkontoMitFolgendemInhaltErstelltHaben(final List<TKontenZeile> zeilen)
    {
        api.haushaltsbuchführung(haushaltsbuch ->
            haushaltsbuch.eröffnungsbilanz(eröffnungsbilanz ->
                eröffnungsbilanz.erstellt()));
    }
}
