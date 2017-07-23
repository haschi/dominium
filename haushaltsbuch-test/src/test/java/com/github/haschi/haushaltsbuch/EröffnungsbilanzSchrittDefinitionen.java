package com.github.haschi.haushaltsbuch;

import cucumber.api.java.de.Angenommen;
import cucumber.api.java.de.Dann;
import cucumber.api.java.de.Wenn;
import jdk.nashorn.internal.runtime.regexp.RegExp;
import jdk.nashorn.internal.runtime.regexp.RegExpFactory;
import jdk.nashorn.internal.runtime.regexp.RegExpMatcher;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

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
        final List<Buchung> buchungen = zeilen.stream().flatMap(z -> Stream.of(buchung(z.haben), buchung(z.soll)))
                .collect(Collectors.toList());

        api.haushaltsbuchführung(haushaltsbuch ->
            haushaltsbuch.eröffnungsbilanz(eröffnungsbilanz ->
                eröffnungsbilanz.erstellt(buchungen)));
    }

    private Buchung buchung(final String haben)
    {
        final RegExp parser = RegExpFactory.create("^(\\w*) (\\d*,\\d\\d EUR)$", "");
        final RegExpMatcher match = parser.match(haben);
        final String group0 = match.group(0);
        final String group1 = match.group(1);
        final String group2 = match.group(2);

        return new Buchung();
    }
}
