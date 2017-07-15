package com.github.haschi.haushaltsbuch.abfrage;

import com.github.haschi.haushaltsbuch.api.Aggregatkennung;
import com.github.haschi.haushaltsbuch.api.ImmutableHaushaltsbuchAngelegt;
import cucumber.api.java.de.Angenommen;
import cucumber.api.java.de.Dann;

public class HaushaltsbuchSteps
{

    private final AutomationApi api;

    public HaushaltsbuchSteps(final AutomationApi api){
        this.api = api;
    }

    final AggregateProxy<HaushaltsbuchTestaggregat> aggregat = new AggregateProxy<>(
            HaushaltsbuchTestaggregat.class,
            Aggregatkennung.neu());

    @Angenommen("^ich habe mit der Haushaltsbuchführung begonnen$")
    public void ichHabeMitDerHaushaltsbuchführungBegonnen()
    {
        final ImmutableHaushaltsbuchAngelegt haushaltsbuchAngelegt = ImmutableHaushaltsbuchAngelegt.of(aggregat.getIdentifier());

        api.haushaltsführungBegonnen(aggregat, haushaltsbuchAngelegt);
    }

    @Dann("^werde ich ein leeres Haushaltsbuch sehen$")
    public void werdeIchEinLeeresHaushaltsbuchSehen()
    {
        final ImmutableHaushaltsbuch leeresHaushaltsbuch = ImmutableHaushaltsbuch.builder()
                .id(aggregat.getIdentifier())
                .build();

        api.werdeIchEinHaushaltsbuchSehen(aggregat.getIdentifier(), leeresHaushaltsbuch);
    }

    @Angenommen("^ich habe nicht mit der Haushaltsbuchführung begonnen$")
    public void ichHabeNichtMitDerHaushaltsbuchführungBegonnen()
    {
    }

    @Dann("^werde ich kein Haushaltsbuch sehen$")
    public void werdeIchKeinHaushaltsbuchSehen()
    {
        api.werdeIchKeinHaushaltsbuchSehen(aggregat.getIdentifier());
    }
}
