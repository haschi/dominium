package com.github.haschi.haushaltsbuch.fixture;

import com.github.haschi.haushaltsbuch.AbstractHaushaltsbuchführungSteps;
import com.github.haschi.haushaltsbuch.api.ImmutableBeginneHaushaltsbuchführung;
import com.github.haschi.haushaltsbuch.api.ImmutableHaushaltsbuchführungBegonnen;
import com.github.haschi.haushaltsbuch.domäne.Haushaltsbuch;
import org.axonframework.test.aggregate.AggregateTestFixture;
import org.axonframework.test.aggregate.ResultValidator;

import java.util.UUID;

public final class HaushaltsbuchführungSteps implements AbstractHaushaltsbuchführungSteps
{
    private final AggregateTestFixture<Haushaltsbuch> fixture;
    private UUID haushaltsbuchId;
    private ResultValidator validator;

    public HaushaltsbuchführungSteps(final AggregateTestFixture<Haushaltsbuch> fixture)
    {
        this.fixture = fixture;
    }

    @Override
    public void beginnen()
    {
        haushaltsbuchId = UUID.randomUUID();

        validator = fixture.when(ImmutableBeginneHaushaltsbuchführung.builder()
                                         .id(haushaltsbuchId)
                                         .build());
    }

    @Override
    public void hauptbuchAngelegt(final UUID haushaltsbuch, final UUID hauptbuch)
    {
        validator.expectEvents(ImmutableHaushaltsbuchführungBegonnen.builder()
                                       .id(haushaltsbuch)
                                       .build());
    }

    @Override
    public UUID aktuellesHaushaltsbuch()
    {
        return haushaltsbuchId;
    }

    @Override
    public UUID aktuellesHauptbuch()
    {
        return null;
    }
}
