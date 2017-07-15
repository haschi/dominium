package com.github.haschi.haushaltsbuch.fixture;

import com.github.haschi.haushaltsbuch.AbstractHaushaltsbuchführungSteps;
import com.github.haschi.haushaltsbuch.api.Aggregatkennung;
import com.github.haschi.haushaltsbuch.api.ImmutableBeginneHaushaltsbuchführung;
import com.github.haschi.haushaltsbuch.api.ImmutableHaushaltsbuchführungBegonnen;
import com.github.haschi.haushaltsbuch.api.ImmutableJournalWurdeAngelegt;
import com.github.haschi.haushaltsbuch.domäne.Haushaltsbuch;
import org.axonframework.messaging.Message;
import org.axonframework.test.aggregate.AggregateTestFixture;
import org.axonframework.test.aggregate.ResultValidator;

import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;

public final class HaushaltsbuchführungSteps implements AbstractHaushaltsbuchführungSteps
{
    private final AggregateTestFixture<Haushaltsbuch> fixture;
    private Aggregatkennung haushaltsbuchId;
    private ResultValidator validator;

    public HaushaltsbuchführungSteps(final AggregateTestFixture<Haushaltsbuch> fixture)
    {
        this.fixture = fixture;
    }

    @Override
    public void beginnen()
    {
        haushaltsbuchId = Aggregatkennung.neu();

        validator = fixture.when(ImmutableBeginneHaushaltsbuchführung.builder()
                                         .id(haushaltsbuchId)
                                         .build());
    }

    @Override
    public void hauptbuchAngelegt(final Aggregatkennung haushaltsbuch, final Aggregatkennung hauptbuch)
    {
        assertThat(istEreignisEingetreten(haushaltsbuch))
                .contains(ImmutableHaushaltsbuchführungBegonnen.builder()
                                  .id(haushaltsbuch)
                                  .build());
    }

    private <T> Stream<Object> istEreignisEingetreten(
            final Aggregatkennung aggregatkennung)
    {
        return fixture.getEventStore().readEvents(aggregatkennung.toString())
                .asStream()
                .map(Message::getPayload);
    }

    @Override
    public Aggregatkennung aktuellesHaushaltsbuch()
    {
        return haushaltsbuchId;
    }

    @Override
    public Aggregatkennung aktuellesHauptbuch()
    {
        return haushaltsbuchId;
    }

    @Override
    public void journalAngelegt(final Aggregatkennung uuid)
    {
        assertThat(istEreignisEingetreten(uuid))
                .contains(ImmutableJournalWurdeAngelegt.builder()
                                  .aktuelleHaushaltsbuchId(aktuellesHaushaltsbuch())
                                  .build());
    }
}
