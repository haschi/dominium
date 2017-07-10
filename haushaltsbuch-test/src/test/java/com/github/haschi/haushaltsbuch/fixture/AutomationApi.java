package com.github.haschi.haushaltsbuch.fixture;

import com.github.haschi.haushaltsbuch.AbstractAutomationApi;
import com.github.haschi.haushaltsbuch.AbstractHaushaltsbuchführungSteps;
import com.github.haschi.haushaltsbuch.domäne.Haushaltsbuch;
import org.axonframework.test.aggregate.AggregateTestFixture;

import java.util.function.Consumer;

public final class AutomationApi implements AbstractAutomationApi
{

    private final AggregateTestFixture<Haushaltsbuch> fixture;
    private final HaushaltsbuchführungSteps haushaltsbuchführungSteps;

    public AutomationApi()
    {
        fixture = new AggregateTestFixture<>(Haushaltsbuch.class);
        haushaltsbuchführungSteps = new HaushaltsbuchführungSteps(fixture);
    }

    @Override
    public void haushaltsbuchführung(final Consumer<AbstractHaushaltsbuchführungSteps> consumer)
    {
        consumer.accept(haushaltsbuchführungSteps);
    }

    @Override
    public void start()
    {

    }

    @Override
    public void stop()
    {

    }
}
