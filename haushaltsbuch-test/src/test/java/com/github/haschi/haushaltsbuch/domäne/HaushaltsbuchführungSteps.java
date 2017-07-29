package com.github.haschi.haushaltsbuch.domäne;

import com.github.haschi.haushaltsbuch.AbstractEröffnungsbilanzSteps;
import com.github.haschi.haushaltsbuch.AbstractHauptbuchSteps;
import com.github.haschi.haushaltsbuch.AbstractHaushaltsbuchführungSteps;
import com.github.haschi.haushaltsbuch.AbstractInventarSteps;
import com.github.haschi.haushaltsbuch.AbstractJournalSteps;
import com.github.haschi.haushaltsbuch.InventarSteps;
import com.github.haschi.haushaltsbuch.InventarZustand;
import com.github.haschi.haushaltsbuch.api.Aggregatkennung;
import com.github.haschi.haushaltsbuch.api.BeginneHaushaltsbuchführung;
import com.github.haschi.haushaltsbuch.api.ImmutableBeginneHaushaltsbuchführung;
import com.github.haschi.haushaltsbuch.api.ImmutableHaushaltsbuchführungBegonnen;
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
    public void hauptbuchAngelegt()
    {
        assertThat(ereignismonitor.erwarteteEreignisse().get(0)).isEqualTo(
                ImmutableHaushaltsbuchführungBegonnen.builder()
                        .id(this.haushaltsbuchführung)
                        .build());
    }

    @Override
    public void aktuellesHauptbuch(final Consumer<AbstractHauptbuchSteps> consumer)
    {
        consumer.accept(new HauptbuchSteps(ereignismonitor));
    }

    @Override
    public void journal(final Consumer<AbstractJournalSteps> consumer)
    {
        consumer.accept(new JournalSteps(ereignismonitor, this.haushaltsbuchführung));
    }

    @Override
    public void inventar(final Consumer<AbstractInventarSteps> consumer)
    {
        consumer.accept(new InventarSteps());
    }

    @Override
    public InventarZustand inventar()
    {
        return null;
    }

    @Override
    public void eröffnungsbilanz(final Consumer<AbstractEröffnungsbilanzSteps> consumer)
    {
        consumer.accept(new EröffnungsbilanzSteps(this.ereignismonitor));
    }
}
