package com.github.haschi.haushaltsbuch.fixture;

import com.github.haschi.haushaltsbuch.AbstractAktuellesInventarSteps;
import com.github.haschi.haushaltsbuch.AbstractInventarSteps;
import com.github.haschi.haushaltsbuch.Vermögenswert;
import com.github.haschi.haushaltsbuch.api.Aggregatkennung;
import com.github.haschi.haushaltsbuch.api.refaktorisiert.ImmutableInventar;
import com.github.haschi.haushaltsbuch.api.refaktorisiert.ImmutablePosition;
import com.github.haschi.haushaltsbuch.domäne.Haushaltsbuch;
import org.axonframework.test.aggregate.AggregateTestFixture;

import java.util.List;
import java.util.stream.Collectors;

public class InventarSteps implements AbstractInventarSteps
{
    private final Ereignisquelle ereignisquelle;
    private final AggregateTestFixture<Haushaltsbuch> fixture;
    private final Aggregatkennung haushaltsbuchId;

    public InventarSteps(
            final Ereignisquelle ereignisquelle, final AggregateTestFixture<Haushaltsbuch> fixture, final
    Aggregatkennung haushaltsbuchId) {
        this.ereignisquelle = ereignisquelle;

        this.fixture = fixture;
        this.haushaltsbuchId = haushaltsbuchId;
    }

    @Override
    public AbstractAktuellesInventarSteps anlegen(final List<Vermögenswert> vermögenswerte)
    {
        final ImmutableInventar inventar1 = ImmutableInventar.builder()
                .addAllVermögenswerte(vermögenswerte.stream()
                                              .map(vw -> ImmutablePosition
                                                      .builder()
                                                      .beschreibung(vw.position)
                                                      .währungsbetrag(vw.betrag)
                                                      .build())
                                              .collect(Collectors.toList()))
                .build();

        return new AktuellesInventarSteps(ereignisquelle, fixture,   haushaltsbuchId, inventar1);
    }
}
