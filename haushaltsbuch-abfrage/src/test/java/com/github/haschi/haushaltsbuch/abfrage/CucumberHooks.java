package com.github.haschi.haushaltsbuch.abfrage;

import cucumber.api.java.After;
import cucumber.api.java.Before;

public class CucumberHooks
{
    private AutomationApi api;

    public CucumberHooks(AutomationApi api)
    {
        this.api = api;
    }

    @Before
    public void cqrsStarten() throws Exception
    {
        api.start();
    }

    @After
    public void stop() throws Exception
    {
        api.stop();
    }
}
