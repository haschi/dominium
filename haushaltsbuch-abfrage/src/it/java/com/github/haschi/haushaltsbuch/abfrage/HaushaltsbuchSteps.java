package com.github.haschi.haushaltsbuch.abfrage;

import com.github.haschi.haushaltsbuch.api.ImmutableHaushaltsbuchAngelegt;
import cucumber.api.java.After;
import cucumber.api.java.Before;
import cucumber.api.java.de.Angenommen;
import cucumber.api.java.de.Dann;

import java.util.UUID;

import static io.restassured.RestAssured.get;

public class HaushaltsbuchSteps
{
    private Steps steps = new Steps();

    final AggregateProxy<HaushaltsbuchTestaggregat> aggregat = new AggregateProxy<>(
            HaushaltsbuchTestaggregat.class,
            UUID.randomUUID());

    @Before
    public void cqrsStarten() throws Exception
    {
        steps.cqrsStarten();
    }

    @After
    public  void cqrsStoppen() {
        steps.stop();
    }

    @Angenommen("^ich habe mit der Haushaltsbuchführung begonnen$")
    public void ichHabeMitDerHaushaltsbuchführungBegonnen()
    {

        final ImmutableHaushaltsbuchAngelegt haushaltsbuchAngelegt = ImmutableHaushaltsbuchAngelegt.of(aggregat.getIdentifier());
        steps.haushaltsführungBegonnen(aggregat, haushaltsbuchAngelegt);
    }

    @Dann("^werde ich ein leeres Haushaltsbuch sehen$")
    public void werdeIchEinLeeresHaushaltsbuchSehen()
    {
        get("http://localhost:8080/haushaltsbuch/" + aggregat.getIdentifier().toString())
                .then()
                .statusCode(200);
    }
}
