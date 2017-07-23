package com.github.haschi.haushaltsbuch.domäne;

import com.github.haschi.haushaltsbuch.api.Aggregatkennung;
import com.github.haschi.haushaltsbuch.api.ImmutableBeginneHaushaltsbuchführung;
import com.github.haschi.haushaltsbuch.api.ImmutableHaushaltsbuchführungBegonnen;
import com.github.haschi.haushaltsbuch.api.ImmutableJournalWurdeAngelegt;
import com.github.haschi.haushaltsbuch.api.refaktorisiert.ImmutableKontenrahmenAngelegt;
import com.github.haschi.haushaltsbuch.api.refaktorisiert.ImmutableKonto;
import com.github.haschi.haushaltsbuch.api.refaktorisiert.Kontoart;
import org.axonframework.commandhandling.CommandHandler;
import org.axonframework.commandhandling.model.AggregateIdentifier;
import org.axonframework.eventsourcing.EventSourcingHandler;

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
        apply(
                ImmutableHaushaltsbuchführungBegonnen.builder()
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
                                      .build())
                      .build());
    }

    @EventSourcingHandler
    public void falls(final ImmutableHaushaltsbuchführungBegonnen ereignis)
    {
        this.id = ereignis.id();
    }
}
