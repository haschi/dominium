package com.github.haschi.haushaltsbuch.domäne;

import com.github.haschi.haushaltsbuch.AbstractHaushaltsbuchführungSteps;
import com.github.haschi.haushaltsbuch.api.BeginneHaushaltsbuchführung;
import com.github.haschi.haushaltsbuch.api.ImmutableBeginneHaushaltsbuchführung;
import com.github.haschi.haushaltsbuch.api.ImmutableHaushaltsbuchführungBegonnen;
import com.github.haschi.haushaltsbuch.api.ImmutableJournalWurdeAngelegt;
import com.github.haschi.haushaltsbuch.infrastruktur.Ereignismonitor;
import org.axonframework.config.Configuration;

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.fail;

public final class HaushaltsbuchführungSteps implements AbstractHaushaltsbuchführungSteps
{
    private final Configuration configuration;
    private final Ereignismonitor ereignismonitor;
    private UUID haushaltsbuchführung;

    public HaushaltsbuchführungSteps(final Configuration configuration, final Ereignismonitor ereignismonitor) {

        this.configuration = configuration;
        this.ereignismonitor = ereignismonitor;
    }

    @Override
    public void beginnen()
    {
        this.haushaltsbuchführung = UUID.randomUUID();

        final BeginneHaushaltsbuchführung beginneHaushaltsbuchführung = ImmutableBeginneHaushaltsbuchführung.builder()
                .id(this.haushaltsbuchführung)
                .build();

        configuration.commandGateway().sendAndWait(beginneHaushaltsbuchführung);
    }

    @Override
    public void hauptbuchAngelegt(final UUID haushaltsbuch, final UUID hauptbuch)
    {
        try
        {
            assertThat(ereignismonitor.nächstesEreignis()).isEqualTo(
                    ImmutableHaushaltsbuchführungBegonnen.builder()
                    .id(this.haushaltsbuchführung)
                    .build());
        }
        catch (final InterruptedException ausnahme)
        {
            fail("Unterbrochen");
        }
    }

    @Override
    public UUID aktuellesHaushaltsbuch()
    {
        return null;
    }

    @Override
    public UUID aktuellesHauptbuch()
    {
        return null;
    }

    @Override
    public void journalAngelegt(final UUID uuid)
    {
        try
        {
            assertThat(ereignismonitor.nächstesEreignis())
                    .isEqualTo(ImmutableJournalWurdeAngelegt.builder()
                               .aktuelleHaushaltsbuchId(this.haushaltsbuchführung)
                                .build());
        }
        catch (final InterruptedException ausnahme)
        {
            fail("Unterbrochen");
        }
    }
}
