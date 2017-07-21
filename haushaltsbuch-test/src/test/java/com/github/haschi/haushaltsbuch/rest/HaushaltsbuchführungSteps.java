package com.github.haschi.haushaltsbuch.rest;

import com.github.haschi.haushaltsbuch.AbstractHaushaltsbuchführungSteps;
import com.github.haschi.haushaltsbuch.api.Aggregatkennung;
import com.github.haschi.haushaltsbuch.api.BeginneHaushaltsbuchführung;
import com.github.haschi.haushaltsbuch.api.ImmutableBeginneHaushaltsbuchführung;
import com.github.haschi.haushaltsbuch.api.ImmutableHaushaltsbuchführungBegonnen;
import com.github.haschi.haushaltsbuch.api.ImmutableJournalWurdeAngelegt;
import org.axonframework.config.Configuration;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.fail;

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
    }

    @Override
    public void hauptbuchAngelegt(final Aggregatkennung haushaltsbuch, final Aggregatkennung hauptbuch)
    {
        try
        {
            assertThat(monitor.nächstesEreignis()).isEqualTo(
                    ImmutableHaushaltsbuchführungBegonnen.builder()
                            .id(haushaltsbuch)
                            .build());
        }
        catch (final InterruptedException e)
        {
            fail("Unterbrochen");
        }
    }

    @Override
    public Aggregatkennung aktuellesHaushaltsbuch()
    {
        return this.aktuellesHaushaltsbuch;
    }

    @Override
    public Aggregatkennung aktuellesHauptbuch()
    {
        return null;
    }

    @Override
    public void journalAngelegt(final Aggregatkennung uuid)
    {
        try
        {
            assertThat(monitor.nächstesEreignis()).isEqualTo(
                    ImmutableJournalWurdeAngelegt.builder()
                    .aktuelleHaushaltsbuchId(uuid)
                    .build());
        }
        catch (final InterruptedException e)
        {
            fail("Unterbrochen");
        }
    }
}
