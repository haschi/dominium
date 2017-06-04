package com.github.haschi.haushaltsbuch.abfrage;

import cucumber.api.java.Before;
import cucumber.api.java.de.Angenommen;

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
        new AggregatProxy()
        steps.haushaltsführungBegonnen();
    }
}
