package com.github.haschi.haushaltsbuch;

import cucumber.api.java.de.Wenn;

public class HaushaltsbuchführungStepdefs
{
    private AbstractAutomationApi api;

    public HaushaltsbuchführungStepdefs(AbstractAutomationApi api) {

        this.api = api;
    }

    @Wenn("^ich mit der Haushaltsbuchführung beginne$")
    public void ichMitDerHaushaltsbuchführungBeginne() throws Throwable
    {
        api.haushaltsbuchführung(AbstractHaushaltsbuchführungSteps::beginnen);
    }
}
