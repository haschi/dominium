package com.github.haschi.haushaltsbuch.domäne;

import com.github.haschi.haushaltsbuch.AbstractHauptbuchSteps;
import com.github.haschi.haushaltsbuch.AbstractHaushaltsbuchführungSteps;
import com.github.haschi.haushaltsbuch.api.Aggregatkennung;
import com.github.haschi.haushaltsbuch.api.BeginneHaushaltsbuchführung;
import com.github.haschi.haushaltsbuch.api.ImmutableBeginneHaushaltsbuchführung;
import com.github.haschi.haushaltsbuch.api.ImmutableHaushaltsbuchführungBegonnen;
import com.github.haschi.haushaltsbuch.api.ImmutableJournalWurdeAngelegt;
import com.github.haschi.haushaltsbuch.infrastruktur.Ereignismonitor;
import org.axonframework.config.Configuration;

import java.util.function.Consumer;

import static org.assertj.core.api.Assertions.assertThat;

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
        ereignismonitor.erwarte(3);
    }

    @Override
    public void hauptbuchAngelegt(final Aggregatkennung haushaltsbuch, final Aggregatkennung hauptbuch)
    {
        assertThat(ereignismonitor.erwarteteEreignisse().get(0)).isEqualTo(
                ImmutableHaushaltsbuchführungBegonnen.builder()
                .id(this.haushaltsbuchführung)
                .build());
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
        assertThat(ereignismonitor.erwarteteEreignisse().get(1))
                .isEqualTo(ImmutableJournalWurdeAngelegt.builder()
                           .aktuelleHaushaltsbuchId(this.haushaltsbuchführung)
                            .build());
    }

    @Override
    public void hauptbuch(final Consumer<AbstractHauptbuchSteps> consumer)
    {
        final AbstractHauptbuchSteps hauptbuchSteps = new HauptbuchSteps(ereignismonitor);
        consumer.accept(hauptbuchSteps);
    }
}
