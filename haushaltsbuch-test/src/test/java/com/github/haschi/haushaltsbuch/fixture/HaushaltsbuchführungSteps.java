package com.github.haschi.haushaltsbuch.fixture;

import com.github.haschi.haushaltsbuch.AbstractEröffnungsbilanzSteps;
import com.github.haschi.haushaltsbuch.AbstractHauptbuchSteps;
import com.github.haschi.haushaltsbuch.AbstractHaushaltsbuchführungSteps;
import com.github.haschi.haushaltsbuch.AbstractInventarSteps;
import com.github.haschi.haushaltsbuch.AbstractJournalSteps;
import com.github.haschi.haushaltsbuch.InventarSteps;
import com.github.haschi.haushaltsbuch.InventarZustand;
import com.github.haschi.haushaltsbuch.api.Aggregatkennung;
import com.github.haschi.haushaltsbuch.api.ImmutableBeginneHaushaltsbuchführung;
import com.github.haschi.haushaltsbuch.api.ImmutableHaushaltsbuchführungBegonnen;
import com.github.haschi.haushaltsbuch.domäne.Haushaltsbuch;
import org.axonframework.messaging.Message;
import org.axonframework.test.aggregate.AggregateTestFixture;

import java.util.function.Consumer;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;

public final class HaushaltsbuchführungSteps implements AbstractHaushaltsbuchführungSteps, Ereignisquelle
{
    private final AggregateTestFixture<Haushaltsbuch> fixture;
    private Aggregatkennung haushaltsbuchId;

    public HaushaltsbuchführungSteps(final AggregateTestFixture<Haushaltsbuch> fixture)
    {
        this.fixture = fixture;
    }

    @Override
    public void beginnen()
    {
        haushaltsbuchId = Aggregatkennung.neu();

        fixture.when(ImmutableBeginneHaushaltsbuchführung.builder()
                             .id(haushaltsbuchId)
                             .build());
    }

    @Override
    public void hauptbuchAngelegt()
    {
        assertThat(ereignisseLesen(haushaltsbuchId))
                .contains(ImmutableHaushaltsbuchführungBegonnen.builder()
                                  .id(haushaltsbuchId)
                                  .build());
    }

    @Override
    public Stream<Object> ereignisseLesen(final Aggregatkennung aggregatkennung)
    {
        return fixture.getEventStore().readEvents(aggregatkennung.toString())
                .asStream()
                .map(Message::getPayload);
    }

    @Override
    public void aktuellesHauptbuch(final Consumer<AbstractHauptbuchSteps> consumer)
    {
        consumer.accept(new HauptbuchSteps(this, haushaltsbuchId));
    }

    @Override
    public void journal(final Consumer<AbstractJournalSteps> consumer)
    {
        consumer.accept(new JournalSteps(this, haushaltsbuchId));
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

    }
}
