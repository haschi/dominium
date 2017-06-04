package com.github.haschi.haushaltsbuch.abfrage;

import com.github.haschi.haushaltsbuch.api.ImmutableHaushaltsbuchAngelegt;
import cucumber.api.java.Before;
import cucumber.api.java.de.Angenommen;
import cucumber.api.java.de.Dann;

import java.util.UUID;

public class HaushaltsbuchSteps
{
    private Steps steps = new Steps();

    @Before
    public void cqrsStarten()
    {
        steps.cqrsStarten();
    }

    @Angenommen("^ich habe mit der Haushaltsbuchführung begonnen$")
    public void ichHabeMitDerHaushaltsbuchführungBegonnen()
    {
        final AggregateProxy<HaushaltsbuchTestaggregat> aggregat = new AggregateProxy<>(
                HaushaltsbuchTestaggregat.class,
                UUID.randomUUID());

        final ImmutableHaushaltsbuchAngelegt haushaltsbuchAngelegt = ImmutableHaushaltsbuchAngelegt.of(aggregat.getIdentifier());
        steps.haushaltsführungBegonnen(aggregat, haushaltsbuchAngelegt);
    }

    @Dann("^werde ich ein leeres Haushaltsbuch sehen$")
    public void werdeIchEinLeeresHaushaltsbuchSehen()
    {
        // Write code here that turns the phrase above into concrete actions
        // throw new PendingException();
    }
}
