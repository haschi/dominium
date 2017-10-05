package org.github.haschi.haushaltsbuch;

import cucumber.api.java.de.Dann;
import cucumber.api.java.de.Wenn;
import org.github.haschi.haushaltsbuch.api.BeginneHaushaltsbuchführung;
import org.github.haschi.haushaltsbuch.api.Eröffnungsbilanzkonto;
import org.github.haschi.haushaltsbuch.api.EröffnungsbilanzkontoErstellt;
import org.github.haschi.haushaltsbuch.api.Inventar;
import org.github.haschi.haushaltsbuch.api.LeseInventar;
import org.github.haschi.haushaltsbuch.api._Buchung;
import org.github.haschi.haushaltsbuch.infrastruktur.modellierung.de.Aggregatkennung;
import org.github.haschi.infrastruktur.Abfragekonfiguration;
import org.github.haschi.infrastruktur.Anweisungskonfiguration;

import java.util.List;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;

public class HaushaltsbuchführungBeginnenSteps
{
    private final DieWelt welt;
    private final Anweisungskonfiguration anweisung;
    private final Abfragekonfiguration abfrage;

    public HaushaltsbuchführungBeginnenSteps(
            final DieWelt welt,
            final Anweisungskonfiguration anweisung,
            final Abfragekonfiguration abfrage)
    {
        this.welt = welt;
        this.anweisung = anweisung;
        this.abfrage = abfrage;
    }

    @Wenn("^ich die Haushaltsbuchführung beginne$")
    public void ichDieHaushaltsbuchführungBeginne()
    {

        welt.aktuellesHaushaltsbuch = Aggregatkennung.neu();

        final Inventar inventar = abfrage.commandGateway().sendAndWait(
                LeseInventar.of(welt.aktuelleInventur));

        anweisung.konfiguration().commandGateway().sendAndWait(
                BeginneHaushaltsbuchführung.builder()
                        .id(welt.aktuellesHaushaltsbuch)
                        .inventar(inventar)
                        .build());
    }

    @Dann("^werde ich folgendes Eröffnungsbilanzkonto im Hauptbuch erstellt haben:$")
    public void werdeIchFolgendesEröffnungsbilanzkontoImHauptbuchErstelltHaben(
            final List<Kontozeile> eröffnungsbilanzkonto) throws Exception
    {
        final EröffnungsbilanzkontoErstellt eröffnungsbilanzkontoErstellt = anweisung.konfiguration().eventStore()
                .readEvents(welt.aktuellesHaushaltsbuch.toString())
                .asStream()
                .filter(m -> m.getPayloadType().isAssignableFrom(EröffnungsbilanzkontoErstellt.class))
                .map(m -> (EröffnungsbilanzkontoErstellt) m.getPayload())
                .findFirst()
                .orElseThrow(() -> new Exception("Kein Eröffnungsbilanzkonto erstellt"));

        assertThat(eröffnungsbilanzkontoErstellt.eröffnungsbilanzkonto())
                .isEqualTo(Eröffnungsbilanzkonto.builder()
                                   .addAllSoll(eröffnungsbilanzkonto.stream()
                                                       .map(zeile -> zeile.soll)
                                                       .filter(buchung -> !buchung.equals(_Buchung.leer()))
                                                       .collect(Collectors.toList()))
                                   .addAllHaben(eröffnungsbilanzkonto.stream()
                                                        .map(zeile -> zeile.haben)
                                                        .filter(buchung -> !buchung.equals(_Buchung.leer()))
                                                        .collect(Collectors.toList()))
                                   .build());
    }
}
