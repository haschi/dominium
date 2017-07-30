package com.github.haschi.haushaltsbuch.domäne;

import com.github.haschi.haushaltsbuch.api.Aggregatkennung;
import com.github.haschi.haushaltsbuch.api.ImmutableBeginneHaushaltsbuchführung;
import com.github.haschi.haushaltsbuch.api.ImmutableHauptbuchWurdeAngelegt;
import com.github.haschi.haushaltsbuch.api.ImmutableHaushaltsbuchführungBegonnen;
import com.github.haschi.haushaltsbuch.api.ImmutableJournalWurdeAngelegt;
import com.github.haschi.haushaltsbuch.api.refaktorisiert.Buchung;
import com.github.haschi.haushaltsbuch.api.refaktorisiert.ImmutableBuchung;
import com.github.haschi.haushaltsbuch.api.refaktorisiert.ImmutableErstelleEröffnungsbilanz;
import com.github.haschi.haushaltsbuch.api.refaktorisiert.ImmutableEröffnungsbilanzkontoErstellt;
import com.github.haschi.haushaltsbuch.api.refaktorisiert.ImmutableKontenrahmenAngelegt;
import com.github.haschi.haushaltsbuch.api.refaktorisiert.ImmutableKonto;
import com.github.haschi.haushaltsbuch.api.refaktorisiert.Kontoart;
import com.github.haschi.haushaltsbuch.api.refaktorisiert.Spalte;
import org.axonframework.commandhandling.CommandHandler;
import org.axonframework.commandhandling.model.AggregateIdentifier;
import org.axonframework.eventsourcing.EventSourcingHandler;

import java.util.List;
import java.util.stream.Collectors;

import static org.axonframework.commandhandling.model.AggregateLifecycle.apply;

public class Haushaltsbuch
{
    @AggregateIdentifier
    Aggregatkennung id;

    public Haushaltsbuch()
    {

    }

    @CommandHandler
    public Haushaltsbuch(final ImmutableBeginneHaushaltsbuchführung anweisung)
    {
        apply(ImmutableHaushaltsbuchführungBegonnen.builder()
                        .id(anweisung.id())
                        .build());

        apply(ImmutableJournalWurdeAngelegt.builder()
                      .aktuelleHaushaltsbuchId(anweisung.id())
                      .build());

        apply(ImmutableKontenrahmenAngelegt.builder()
                      .addKonten(
                              ImmutableKonto.builder()
                                      .nummer("01")
                                      .bezeichnung("Sparbuch")
                                      .art(Kontoart.Aktiv)
                                      .build(),
                              ImmutableKonto.builder()
                                      .nummer("21")
                                      .bezeichnung("Geldbörse")
                                      .art(Kontoart.Aktiv)
                                      .build(),

                              ImmutableKonto.builder()
                                      .nummer("22")
                                      .bezeichnung("Bankkonto")
                                      .art(Kontoart.Aktiv)
                                      .build(),
                              ImmutableKonto.builder()
                                      .nummer("41")
                                      .bezeichnung("Bankkredit")
                                      .art(Kontoart.Passiv)
                                      .build(),
                              ImmutableKonto.builder()
                                      .nummer("51")
                                      .bezeichnung("Gehalt")
                                      .art(Kontoart.Ertrag)
                                      .build(),
                              ImmutableKonto.builder()
                                      .nummer("61")
                                      .bezeichnung("Miete")
                                      .art(Kontoart.Aufwand)
                                      .build(),
                              ImmutableKonto.builder()
                                      .nummer("80")
                                      .bezeichnung("Eröffnungsbilanzkonto")
                                      .art(Kontoart.Bilanz)
                                      .build())
                      .build());

        apply(ImmutableHauptbuchWurdeAngelegt.builder()
                      .haushaltsbuchId(anweisung.id())
                      .build());
    }

    @EventSourcingHandler
    public void falls(final ImmutableHaushaltsbuchführungBegonnen ereignis)
    {
        this.id = ereignis.id();
    }

    @CommandHandler
    public void erstelleEröffnungsbilanz(final ImmutableErstelleEröffnungsbilanz anweisung) {

        final List<ImmutableBuchung> buchungen =
                anweisung.inventar().vermögenswerte().stream()
                .map(vw -> ImmutableBuchung.builder()
                    .spalte(Spalte.haben)
                    .text(vw.beschreibung())
                    .betrag(vw.währungsbetrag())
                    .build())
                .collect(Collectors.toList());

        apply(ImmutableEröffnungsbilanzkontoErstellt.builder()
            .addAllBuchungen(buchungen)
              .build());
    }
}
