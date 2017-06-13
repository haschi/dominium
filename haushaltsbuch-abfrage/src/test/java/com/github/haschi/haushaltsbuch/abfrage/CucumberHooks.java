package com.github.haschi.haushaltsbuch.abfrage;

import cucumber.api.Scenario;
import cucumber.api.java.After;
import cucumber.api.java.Before;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.stream.Collectors;

public class CucumberHooks
{
    Logger log = LoggerFactory.getLogger(CucumberHooks.class);

    private AutomationApi api;

    public CucumberHooks(AutomationApi api)
    {
        this.api = api;
    }

    @Before
    public void cqrsStarten(Scenario sceanrio) throws Exception
    {
        log.debug(sceanrio.getSourceTagNames().stream().collect(Collectors.joining(", ")));
        api.start();
    }

    @After
    public void stop() throws Exception
    {
        api.stop();
    }
}
