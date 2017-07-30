package com.github.haschi.haushaltsbuch.fixture;

import com.github.haschi.haushaltsbuch.AbstractAktuellesInventarSteps;
import com.github.haschi.haushaltsbuch.AbstractEröffnungsbilanzSteps;
import com.github.haschi.haushaltsbuch.api.Aggregatkennung;
import com.github.haschi.haushaltsbuch.api.refaktorisiert.Inventar;
import com.github.haschi.haushaltsbuch.domäne.Haushaltsbuch;
import org.axonframework.test.aggregate.AggregateTestFixture;

import java.util.function.Consumer;

public class AktuellesInventarSteps implements AbstractAktuellesInventarSteps
{
    private final Ereignisquelle ereignisquelle;
    private final AggregateTestFixture<Haushaltsbuch> fixture;
    private final Aggregatkennung haushaltsbuchid;
    private final Inventar inventar;

    public AktuellesInventarSteps(
            final Ereignisquelle ereignisquelle,
            final AggregateTestFixture<Haushaltsbuch> fixture,
            final Aggregatkennung haushaltsbuchid,
            final Inventar inventar) {
        this.ereignisquelle = ereignisquelle;
        this.fixture = fixture;
        this.haushaltsbuchid = haushaltsbuchid;
        this.inventar = inventar;
    }

    @Override
    public void eröffnungsbilanz(final Consumer<AbstractEröffnungsbilanzSteps> consumer)
    {
        consumer.accept(new EröffnungsbilanzSteps(fixture, ereignisquelle, haushaltsbuchid, inventar));
    }
}
