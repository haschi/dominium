package com.github.haschi.haushaltsbuch.abfrage;

import com.github.haschi.haushaltsbuch.api.ImmutableHaushaltsbuchAngelegt;
import cucumber.api.java.de.Angenommen;
import cucumber.api.java.de.Dann;

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

public class HaushaltsbuchSteps
{
    private AutomationApi api;

    public HaushaltsbuchSteps(AutomationApi api)
    {
        this.api = api;
    }

    final AggregateProxy<HaushaltsbuchTestaggregat> aggregat = new AggregateProxy<>(
            HaushaltsbuchTestaggregat.class,
            UUID.randomUUID());

    @Angenommen("^ich habe mit der Haushaltsbuchführung begonnen$")
    public void ichHabeMitDerHaushaltsbuchführungBegonnen()
    {
        final ImmutableHaushaltsbuchAngelegt haushaltsbuchAngelegt = ImmutableHaushaltsbuchAngelegt.of(aggregat.getIdentifier());

        api.haushaltsführungBegonnen(aggregat, haushaltsbuchAngelegt);
    }

    @Dann("^werde ich ein leeres Haushaltsbuch sehen$")
    public void werdeIchEinLeeresHaushaltsbuchSehen()
    {
        assertThat(api.haushaltsbuch(aggregat.getIdentifier()))
                .isEqualTo(ImmutableHaushaltsbuch.builder().build());
    }
}
