package com.github.haschi.haushaltsbuch.fixture;

import com.github.haschi.haushaltsbuch.AbstractAktuellesHaushaltsbuchSteps;
import com.github.haschi.haushaltsbuch.AbstractAktuellesInventarSteps;
import com.github.haschi.haushaltsbuch.AbstractHauptbuchSteps;
import com.github.haschi.haushaltsbuch.AbstractJournalSteps;
import com.github.haschi.haushaltsbuch.AbstractKontenrahmenSteps;
import com.github.haschi.haushaltsbuch.StepBuilder;
import com.github.haschi.haushaltsbuch.StepConsumer;
import com.github.haschi.haushaltsbuch.api.Aggregatkennung;
import com.github.haschi.haushaltsbuch.domäne.Haushaltsbuch;
import org.axonframework.test.aggregate.AggregateTestFixture;

import java.util.Optional;
import java.util.function.Consumer;

public class AktuellesHaushaltsbuchSteps implements AbstractAktuellesHaushaltsbuchSteps
{
    private final AggregateTestFixture<Haushaltsbuch> fixture;
    private final Ereignisquelle ereignisquelle;
    private final Aggregatkennung haushaltsbuchId;
    private AbstractAktuellesInventarSteps aktuellesInventar;

    public AktuellesHaushaltsbuchSteps(
            final AggregateTestFixture<Haushaltsbuch> fixture,
            final Ereignisquelle ereignisquelle,
            final Aggregatkennung haushaltsbuchId)
    {
        this.fixture = fixture;

        this.ereignisquelle = ereignisquelle;
        this.haushaltsbuchId = haushaltsbuchId;
    }

    @Override
    public void hauptbuch(final Consumer<AbstractHauptbuchSteps> consumer)
    {
        consumer.accept(new HauptbuchSteps(ereignisquelle, haushaltsbuchId));
    }

    @Override
    public void inventar(final StepBuilder builder)
    {
        aktuellesInventar = builder.create(new InventarSteps(ereignisquelle, fixture, haushaltsbuchId));
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
        consumer.accept(new JournalSteps(ereignisquelle, haushaltsbuchId));
    }

    @Override
    public void kontenrahmen(final Consumer<AbstractKontenrahmenSteps> consumer)
    {
        consumer.accept(new KontenrahmenSteps(ereignisquelle, haushaltsbuchId));
    }
}
