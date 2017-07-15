package com.github.haschi.haushaltsbuch.domäne;

import com.github.haschi.haushaltsbuch.api.ImmutableBeginneHaushaltsbuchführung;
import com.github.haschi.haushaltsbuch.api.ImmutableHaushaltsbuchführungBegonnen;
import com.github.haschi.haushaltsbuch.api.ImmutableJournalWurdeAngelegt;
import org.axonframework.commandhandling.CommandHandler;
import org.axonframework.commandhandling.model.AggregateIdentifier;
import org.axonframework.eventsourcing.EventSourcingHandler;

import java.util.UUID;

import static org.axonframework.commandhandling.model.AggregateLifecycle.apply;

public class Haushaltsbuch
{
    @AggregateIdentifier
    UUID id;

    public Haushaltsbuch() {

    }

    @CommandHandler
    public Haushaltsbuch(ImmutableBeginneHaushaltsbuchführung anweisung) {
        apply(
        ImmutableHaushaltsbuchführungBegonnen.builder()
                .id(anweisung.id())
                .build());

        apply(ImmutableJournalWurdeAngelegt.builder()
            .aktuelleHaushaltsbuchId(anweisung.id())
            .build());
    }

    @EventSourcingHandler
    public void falls(ImmutableHaushaltsbuchführungBegonnen ereignis) {
        this.id = ereignis.id();
    }
}
