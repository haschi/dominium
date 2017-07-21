package com.github.haschi.haushaltsbuch.rest;

import com.github.haschi.haushaltsbuch.AbstractHauptbuchSteps;
import com.github.haschi.haushaltsbuch.AbstractHaushaltsbuchführungSteps;
import com.github.haschi.haushaltsbuch.AbstractJournalSteps;
import com.github.haschi.haushaltsbuch.api.Aggregatkennung;
import com.github.haschi.haushaltsbuch.api.BeginneHaushaltsbuchführung;
import com.github.haschi.haushaltsbuch.api.ImmutableBeginneHaushaltsbuchführung;
import com.github.haschi.haushaltsbuch.api.ImmutableHaushaltsbuchführungBegonnen;
import com.github.haschi.haushaltsbuch.api.ImmutableJournalWurdeAngelegt;
import org.apache.commons.lang3.NotImplementedException;
import org.axonframework.config.Configuration;

import java.util.function.Consumer;

import static org.assertj.core.api.Assertions.assertThat;

public final class HaushaltsbuchführungSteps implements AbstractHaushaltsbuchführungSteps
{

    private final Configuration konfiguration;
    private final Synchronisierungsmonitor monitor;
    private Aggregatkennung aktuellesHaushaltsbuch;

    public HaushaltsbuchführungSteps(final Configuration konfiguration, final Synchronisierungsmonitor monitor)
    {
        this.konfiguration = konfiguration;
        this.monitor = monitor;
    }

    @Override
    public void beginnen()
    {
        this.aktuellesHaushaltsbuch = Aggregatkennung.neu();
        final BeginneHaushaltsbuchführung beginneHaushaltsbuchführung = ImmutableBeginneHaushaltsbuchführung.builder()
                .id(this.aktuellesHaushaltsbuch)
                .build();

        konfiguration.commandGateway().sendAndWait(beginneHaushaltsbuchführung);
        monitor.erwarte(3);
    }

    @Override
    public void hauptbuchAngelegt()
    {
        assertThat(monitor.erwarteteEreignisse().get(0)).isEqualTo(
                ImmutableHaushaltsbuchführungBegonnen.builder()
                        .id(this.aktuellesHaushaltsbuch)
                        .build());
    }

    @Override
    public void aktuellesHauptbuch(final Consumer<AbstractHauptbuchSteps> consumer)
    {
        throw new NotImplementedException("Nicht implementiert");
    }

    @Override
    public void journalAngelegt(final Aggregatkennung uuid)
    {
        assertThat(monitor.erwarteteEreignisse().get(1)).isEqualTo(
                ImmutableJournalWurdeAngelegt.builder()
                        .aktuelleHaushaltsbuchId(uuid)
                        .build());
    }

    @Override
    public void journal(final Consumer<AbstractJournalSteps> consumer)
    {
        consumer.accept(new JournalSteps(monitor, aktuellesHaushaltsbuch));
    }
}
