package com.github.haschi.haushaltsbuch.rest;

import com.github.haschi.haushaltsbuch.AbstractHaushaltsbuchführungSteps;
import com.github.haschi.haushaltsbuch.api.BeginneHaushaltsbuchführung;
import com.github.haschi.haushaltsbuch.api.ImmutableBeginneHaushaltsbuchführung;
import com.github.haschi.haushaltsbuch.api.ImmutableHaushaltsbuchführungBegonnen;
import org.axonframework.config.Configuration;

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.fail;

public final class HaushaltsbuchführungSteps implements AbstractHaushaltsbuchführungSteps
{

    private final Configuration konfiguration;
    private final Ereignismonitor monitor;
    private UUID aktuellesHaushaltsbuch;

    public HaushaltsbuchführungSteps(final Configuration konfiguration, final Ereignismonitor monitor)
    {
        this.konfiguration = konfiguration;
        this.monitor = monitor;
    }

    @Override
    public void beginnen()
    {
        this.aktuellesHaushaltsbuch = UUID.randomUUID();
        final BeginneHaushaltsbuchführung beginneHaushaltsbuchführung = ImmutableBeginneHaushaltsbuchführung.builder()
                .id(this.aktuellesHaushaltsbuch)
                .build();

        konfiguration.commandGateway().sendAndWait(beginneHaushaltsbuchführung);
    }

    @Override
    public void hauptbuchAngelegt(final UUID haushaltsbuch, final UUID hauptbuch)
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
    public UUID aktuellesHaushaltsbuch()
    {
        return this.aktuellesHaushaltsbuch;
    }

    @Override
    public UUID aktuellesHauptbuch()
    {
        return null;
    }
}
