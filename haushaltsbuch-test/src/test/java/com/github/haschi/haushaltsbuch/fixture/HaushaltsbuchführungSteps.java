package com.github.haschi.haushaltsbuch.fixture;

import com.github.haschi.haushaltsbuch.AbstractHauptbuchSteps;
import com.github.haschi.haushaltsbuch.AbstractHaushaltsbuchführungSteps;
import com.github.haschi.haushaltsbuch.AbstractJournalSteps;
import com.github.haschi.haushaltsbuch.api.Aggregatkennung;
import com.github.haschi.haushaltsbuch.api.ImmutableBeginneHaushaltsbuchführung;
import com.github.haschi.haushaltsbuch.api.ImmutableHaushaltsbuchführungBegonnen;
import com.github.haschi.haushaltsbuch.api.ImmutableJournalWurdeAngelegt;
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
    public <T> Stream<Object> ereignisseLesen(final Aggregatkennung aggregatkennung)
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
    public void journalAngelegt(final Aggregatkennung uuid)
    {
        assertThat(ereignisseLesen(uuid))
                .contains(ImmutableJournalWurdeAngelegt.builder()
                                  .aktuelleHaushaltsbuchId(haushaltsbuchId)
                                  .build());
    }

    @Override
    public void journal(final Consumer<AbstractJournalSteps> consumer)
    {
        consumer.accept(new JournalSteps(this, haushaltsbuchId));
    }
}
