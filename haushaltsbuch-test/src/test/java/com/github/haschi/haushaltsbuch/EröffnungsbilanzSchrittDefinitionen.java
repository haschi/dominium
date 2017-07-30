package com.github.haschi.haushaltsbuch;

import com.github.haschi.haushaltsbuch.api.refaktorisiert.Buchung;
import com.github.haschi.haushaltsbuch.api.refaktorisiert.ImmutableBuchung;
import cucumber.api.java.de.Angenommen;
import cucumber.api.java.de.Dann;
import cucumber.api.java.de.Wenn;

import java.util.List;
import java.util.stream.Collectors;

public class EröffnungsbilanzSchrittDefinitionen
{
    AbstractAutomationApi api;

    public EröffnungsbilanzSchrittDefinitionen(final AbstractAutomationApi api) {

        this.api = api;
    }

    @Angenommen("^ich habe folgende Vermögenswerte in meinem Inventar erfasst:$")
    public void ichHabeFolgendeVermögenswerteInMeinemInventarErfasst(final List<Vermögenswert> vermögenswerte)
    {
        api.haushaltsbuchführung(
                haushaltsbuch -> haushaltsbuch.aktuellesHaushaltsbuch(
                        aktuell -> aktuell.inventar(
                                inventar -> (inventar.anlegen(vermögenswerte)))));
    }

    @Wenn("^ich die Eröffnungsbilanz aus dem Inventar erstelle$")
    public void ichDieEröffnungsbilanzAusDemInventarErstelle()
    {
        api.haushaltsbuchführung(
            haushaltsbuch -> haushaltsbuch.aktuellesHaushaltsbuch(
                aktuell -> aktuell.inventar((StepConsumer)
                    inventar -> inventar.eröffnungsbilanz(
                            eröffnungsbilanz -> eröffnungsbilanz.erstellen()))));

    }

    @Dann("^werde ich ein Eröffnungsbilanzkonto mit folgendem Inhalt erstellt haben:$")
    public void werdeIchEinEröffnungsbilanzkontoMitFolgendemInhaltErstelltHaben(final List<Buchung2> zeilen)
    {
        final List<Buchung> buchungen = zeilen.stream()
                .map(b -> ImmutableBuchung.builder()
                    .text(b.buchungstext)
                    .betrag(b.währungsbetrag)
                        .spalte(b.spalte)

                        .build())
                .collect(Collectors.toList());

        api.haushaltsbuchführung(
                haushaltsbuch -> haushaltsbuch.aktuellesHaushaltsbuch(
                        aktuell -> aktuell.inventar((StepConsumer)
                            inventar -> inventar.eröffnungsbilanz(
                                    eröffnungsbilanz -> eröffnungsbilanz.erstellt(buchungen)))));
    }
}
