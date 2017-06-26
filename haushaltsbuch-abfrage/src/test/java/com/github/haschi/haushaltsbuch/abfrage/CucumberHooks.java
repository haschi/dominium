package com.github.haschi.haushaltsbuch.abfrage;

import cucumber.api.Scenario;
import cucumber.api.java.After;
import cucumber.api.java.Before;

import java.util.stream.Collectors;

public class CucumberHooks
{
    // Logger log = LoggerFactory.getLogger(CucumberHooks.class);

    private AutomationApi api;

    public CucumberHooks(AutomationApi api)
    {
        this.api = api;
    }

    @Before
    public void cqrsStarten(Scenario sceanrio) throws Exception
    {
        final String tags = sceanrio.getSourceTagNames().stream().collect(Collectors.joining(", "));

        // log.info(MessageFormat.format("Cucumber Before Tags: {0}", tags));

        if (!sceanrio.getSourceTagNames().contains(api.requiredTag())) {
            throw new IllegalStateException();
        }

        api.start();
    }

    @After
    public void stop() throws Exception
    {
        api.stop();
    }
}
