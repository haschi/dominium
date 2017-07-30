package com.github.haschi.haushaltsbuch.domäne;

import com.github.haschi.haushaltsbuch.AbstractAktuellesHaushaltsbuchSteps;
import com.github.haschi.haushaltsbuch.AbstractAktuellesInventarSteps;
import com.github.haschi.haushaltsbuch.AbstractHauptbuchSteps;
import com.github.haschi.haushaltsbuch.AbstractJournalSteps;
import com.github.haschi.haushaltsbuch.AbstractKontenrahmenSteps;
import com.github.haschi.haushaltsbuch.StepBuilder;
import com.github.haschi.haushaltsbuch.StepConsumer;
import com.github.haschi.haushaltsbuch.api.Aggregatkennung;
import com.github.haschi.haushaltsbuch.infrastruktur.Ereignismonitor;
import org.axonframework.config.Configuration;

import java.util.Optional;
import java.util.function.Consumer;

public class AktuellesHaushaltsbuchSteps implements AbstractAktuellesHaushaltsbuchSteps
{
    private final Configuration configuration;
    private final Ereignismonitor monitor;
    private final Aggregatkennung haushaltsbuchId;
    private final InventarSteps inventarSteps;
    private AbstractAktuellesInventarSteps aktuellesInventar;

    public AktuellesHaushaltsbuchSteps(final Configuration configuration, final Ereignismonitor monitor, final
    Aggregatkennung
            haushaltsbuchId)
    {
        this.configuration = configuration;
        this.monitor = monitor;
        this.haushaltsbuchId = haushaltsbuchId;
        this.inventarSteps = new InventarSteps(configuration, monitor, haushaltsbuchId);
    }

    @Override
    public void hauptbuch(final Consumer<AbstractHauptbuchSteps> consumer)
    {
        consumer.accept(new HauptbuchSteps(monitor, haushaltsbuchId));
    }

    @Override
    public void inventar(final StepBuilder builder)
    {
        this.aktuellesInventar = builder.create(inventarSteps);
    }

    @Override
    public void inventar(final StepConsumer consumer)
    {
        consumer.apply(Optional.ofNullable(aktuellesInventar)
            .orElseThrow(IllegalStateException::new));
    }

    @Override
    public void journal(final Consumer<AbstractJournalSteps> consumer)
    {
        consumer.accept(new JournalSteps(monitor, haushaltsbuchId));
    }

    @Override
    public void kontenrahmen(final Consumer<AbstractKontenrahmenSteps> consumer)
    {
        consumer.accept(new KontenrahmenSteps(monitor));
    }
}
