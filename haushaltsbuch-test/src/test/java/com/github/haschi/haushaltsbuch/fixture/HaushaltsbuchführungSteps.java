package com.github.haschi.haushaltsbuch.fixture;

import com.github.haschi.haushaltsbuch.AbstractAktuellesHaushaltsbuchSteps;
import com.github.haschi.haushaltsbuch.AbstractHaushaltsbuchführungSteps;
import com.github.haschi.haushaltsbuch.api.Aggregatkennung;
import com.github.haschi.haushaltsbuch.api.ImmutableBeginneHaushaltsbuchführung;
import com.github.haschi.haushaltsbuch.domäne.Haushaltsbuch;
import org.axonframework.messaging.Message;
import org.axonframework.test.aggregate.AggregateTestFixture;

import java.util.Optional;
import java.util.function.Consumer;
import java.util.stream.Stream;

public final class HaushaltsbuchführungSteps implements AbstractHaushaltsbuchführungSteps, Ereignisquelle
{
    private final AggregateTestFixture<Haushaltsbuch> fixture;
    private Aggregatkennung haushaltsbuchId;

    private Optional<AbstractAktuellesHaushaltsbuchSteps> aktuellesHaushaltsbuchSteps = Optional.empty();

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

        this.aktuellesHaushaltsbuchSteps = Optional.of(new AktuellesHaushaltsbuchSteps(fixture, this, haushaltsbuchId));
    }

    @Override
    public void aktuellesHaushaltsbuch(final Consumer<AbstractAktuellesHaushaltsbuchSteps> consumer)
    {
        consumer.accept(aktuellesHaushaltsbuchSteps.orElseThrow(IllegalStateException::new));
    }

    @Override
    public Stream<Object> ereignisseLesen(final Aggregatkennung aggregatkennung)
    {
        return fixture.getEventStore().readEvents(aggregatkennung.toString())
                .asStream()
                .map(Message::getPayload);
    }
}
