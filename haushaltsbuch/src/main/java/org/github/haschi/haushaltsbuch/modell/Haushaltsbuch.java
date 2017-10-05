package org.github.haschi.haushaltsbuch.modell;

import org.axonframework.commandhandling.CommandHandler;
import org.axonframework.commandhandling.model.AggregateIdentifier;
import org.axonframework.commandhandling.model.AggregateLifecycle;
import org.axonframework.eventsourcing.EventSourcingHandler;
import org.github.haschi.haushaltsbuch.api.BeginneHaushaltsbuchführung;
import org.github.haschi.haushaltsbuch.api.Buchung;
import org.github.haschi.haushaltsbuch.api.Eröffnungsbilanzkonto;
import org.github.haschi.haushaltsbuch.api.EröffnungsbilanzkontoErstellt;
import org.github.haschi.haushaltsbuch.api.HaushaltsbuchführungBegonnen;
import org.github.haschi.haushaltsbuch.api.Inventar;
import org.github.haschi.haushaltsbuch.api.Währungsbetrag;
import org.github.haschi.haushaltsbuch.infrastruktur.modellierung.de.Aggregatkennung;

public class Haushaltsbuch
{

    @AggregateIdentifier
    private Aggregatkennung id;

    public Haushaltsbuch()
    {
    }

    @CommandHandler
    public Haushaltsbuch(final BeginneHaushaltsbuchführung anweisung)
    {
        AggregateLifecycle.apply(
                HaushaltsbuchführungBegonnen.builder()
                        .id(anweisung.id())
                        .build());

        final Inventar inventar = anweisung.inventar();

        final Währungsbetrag eigenkapital = Währungsbetrag.of(
                inventar.anlagevermögen().summe().wert()
                        .add(inventar.umlaufvermögen().summe().wert())
                        .subtract(inventar.schulden().summe().wert()));

        final Eröffnungsbilanzkonto eröffnungsbilanz = Eröffnungsbilanzkonto.builder()
                .addHaben(Buchung.builder()
                                  .buchungstext("Anlagevermögen (AV)")
                                  .betrag(inventar.anlagevermögen().summe())
                                  .build())
                .addHaben(Buchung.builder()
                                  .buchungstext("Umlaufvermögen (UV)")
                                  .betrag(inventar.umlaufvermögen().summe())
                                  .build())
                .addSoll(Buchung.builder()
                                 .buchungstext("Eigenkapital (EK)")
                                 .betrag(eigenkapital)
                                 .build())
                .addSoll(Buchung.builder()
                                 .buchungstext("Fremdkapital (FK)")
                                 .betrag(inventar.schulden().summe())
                                 .build())
                .build();

        AggregateLifecycle.apply(
                EröffnungsbilanzkontoErstellt.builder()
                        .eröffnungsbilanzkonto(eröffnungsbilanz)
                        .build());
    }

    @EventSourcingHandler
    public void falls(final HaushaltsbuchführungBegonnen ereignis)
    {
        id = ereignis.id();
    }
}
