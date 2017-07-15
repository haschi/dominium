package com.github.haschi.haushaltsbuch.domäne;

import com.github.haschi.haushaltsbuch.AbstractHaushaltsbuchführungSteps;
import com.github.haschi.haushaltsbuch.api.Aggregatkennung;
import com.github.haschi.haushaltsbuch.api.BeginneHaushaltsbuchführung;
import com.github.haschi.haushaltsbuch.api.ImmutableBeginneHaushaltsbuchführung;
import com.github.haschi.haushaltsbuch.api.ImmutableHaushaltsbuchführungBegonnen;
import com.github.haschi.haushaltsbuch.api.ImmutableJournalWurdeAngelegt;
import com.github.haschi.haushaltsbuch.infrastruktur.Ereignismonitor;
import org.axonframework.config.Configuration;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.fail;

public final class HaushaltsbuchführungSteps implements AbstractHaushaltsbuchführungSteps
{
    private final Configuration configuration;
    private final Ereignismonitor ereignismonitor;
    private Aggregatkennung haushaltsbuchführung;

    public HaushaltsbuchführungSteps(final Configuration configuration, final Ereignismonitor ereignismonitor) {

        this.configuration = configuration;
        this.ereignismonitor = ereignismonitor;
    }

    @Override
    public void beginnen()
    {
        this.haushaltsbuchführung = Aggregatkennung.neu();

        final BeginneHaushaltsbuchführung beginneHaushaltsbuchführung = ImmutableBeginneHaushaltsbuchführung.builder()
                .id(this.haushaltsbuchführung)
                .build();

        configuration.commandGateway().sendAndWait(beginneHaushaltsbuchführung);
    }

    @Override
    public void hauptbuchAngelegt(final Aggregatkennung haushaltsbuch, final Aggregatkennung hauptbuch)
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
    public Aggregatkennung aktuellesHaushaltsbuch()
    {
        return null;
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
