package com.github.haschi.haushaltsbuch.rest;

import com.github.haschi.haushaltsbuch.AbstractHaushaltsbuchführungSteps;
import com.github.haschi.haushaltsbuch.api.BeginneHaushaltsbuchführung;
import com.github.haschi.haushaltsbuch.api.ImmutableBeginneHaushaltsbuchführung;
import com.github.haschi.haushaltsbuch.api.ImmutableHaushaltsbuchführungBegonnen;
import org.axonframework.config.Configuration;

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.fail;

public class HaushaltsbuchführungSteps implements AbstractHaushaltsbuchführungSteps
{

    private Configuration konfiguration;
    private Ereignismonitor monitor;
    private UUID aktuellesHaushaltsbuch;

    public HaushaltsbuchführungSteps(Configuration konfiguration, Ereignismonitor monitor) {

        this.konfiguration = konfiguration;
        this.monitor = monitor;
    }
    @Override
    public void beginnen()
    {
        this.aktuellesHaushaltsbuch = UUID.randomUUID();
        BeginneHaushaltsbuchführung beginneHaushaltsbuchführung = ImmutableBeginneHaushaltsbuchführung.builder()
                .id(this.aktuellesHaushaltsbuch)
                .build();

        konfiguration.commandGateway().sendAndWait(beginneHaushaltsbuchführung);
    }

    @Override
    public void hauptbuchAngelegt(UUID haushaltsbuch, UUID hauptbuch)
    {
        try
        {
            assertThat(monitor.nächstesEreignis()).isEqualTo(
                    ImmutableHaushaltsbuchführungBegonnen.builder()
                    .id(haushaltsbuch)
                    .build());
        } catch (InterruptedException e)
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
