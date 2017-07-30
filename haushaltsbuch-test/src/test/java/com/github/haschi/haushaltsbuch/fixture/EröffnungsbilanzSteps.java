package com.github.haschi.haushaltsbuch.fixture;

import com.github.haschi.haushaltsbuch.AbstractEröffnungsbilanzSteps;
import com.github.haschi.haushaltsbuch.api.Aggregatkennung;
import com.github.haschi.haushaltsbuch.api.refaktorisiert.Buchung;
import com.github.haschi.haushaltsbuch.api.refaktorisiert.ImmutableErstelleEröffnungsbilanz;
import com.github.haschi.haushaltsbuch.api.refaktorisiert.ImmutableEröffnungsbilanzkontoErstellt;
import com.github.haschi.haushaltsbuch.api.refaktorisiert.Inventar;
import com.github.haschi.haushaltsbuch.domäne.Haushaltsbuch;
import org.axonframework.test.aggregate.AggregateTestFixture;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class EröffnungsbilanzSteps implements AbstractEröffnungsbilanzSteps
{
    private final AggregateTestFixture<Haushaltsbuch> fixture;
    private final Ereignisquelle ereignisquelle;
    private final Aggregatkennung haushaltsbuchId;
    private final Inventar inventar;

    public EröffnungsbilanzSteps(
            final AggregateTestFixture<Haushaltsbuch> fixture,
            final Ereignisquelle ereignisquelle,
            final Aggregatkennung haushaltsbuchId,
            final Inventar inventar) {
        this.fixture = fixture;
        this.ereignisquelle = ereignisquelle;
        this.haushaltsbuchId = haushaltsbuchId;
        this.inventar = inventar;
    }

    @Override
    public void erstellen()
    {
        fixture.when(
                ImmutableErstelleEröffnungsbilanz.builder()
                        .id(haushaltsbuchId)
                        .inventar(inventar)
                        .build());
    }

    @Override
    public void erstellt(final List<Buchung> buchungen)
    {
        assertThat(ereignisquelle.ereignisseLesen(haushaltsbuchId))
                .contains(ImmutableEröffnungsbilanzkontoErstellt.builder()
                          .addAllBuchungen(buchungen)
                          .build());
    }
}
