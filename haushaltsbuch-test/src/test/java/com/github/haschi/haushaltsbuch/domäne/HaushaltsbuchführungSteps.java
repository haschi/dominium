package com.github.haschi.haushaltsbuch.domäne;

import com.github.haschi.haushaltsbuch.AbstractAktuellesHaushaltsbuchSteps;
import com.github.haschi.haushaltsbuch.AbstractHaushaltsbuchführungSteps;
import com.github.haschi.haushaltsbuch.api.Aggregatkennung;
import com.github.haschi.haushaltsbuch.api.BeginneHaushaltsbuchführung;
import com.github.haschi.haushaltsbuch.api.ImmutableBeginneHaushaltsbuchführung;
import com.github.haschi.haushaltsbuch.infrastruktur.Ereignismonitor;
import org.axonframework.config.Configuration;

import java.util.Optional;
import java.util.function.Consumer;

public final class HaushaltsbuchführungSteps implements AbstractHaushaltsbuchführungSteps
{
    private final Configuration configuration;
    private final Ereignismonitor ereignismonitor;

    private Optional<AktuellesHaushaltsbuchSteps> aktuellesHaushaltsbuch;

    public HaushaltsbuchführungSteps(final Configuration configuration, final Ereignismonitor ereignismonitor) {

        this.configuration = configuration;
        this.ereignismonitor = ereignismonitor;
    }

    @Override
    public void beginnen()
    {
        final Aggregatkennung haushaltsbuchId = Aggregatkennung.neu();

        final BeginneHaushaltsbuchführung beginneHaushaltsbuchführung = ImmutableBeginneHaushaltsbuchführung.builder()
                .id(haushaltsbuchId)
                .build();

        configuration.commandGateway().sendAndWait(beginneHaushaltsbuchführung);
        ereignismonitor.erwarte(4);

        this.aktuellesHaushaltsbuch = Optional.of(new AktuellesHaushaltsbuchSteps(
                configuration,
                ereignismonitor,
                haushaltsbuchId));
    }

    @Override
    public void aktuellesHaushaltsbuch(final Consumer<AbstractAktuellesHaushaltsbuchSteps> consumer)
    {
        consumer.accept(this.aktuellesHaushaltsbuch.orElseThrow(IllegalStateException::new));
    }
}
