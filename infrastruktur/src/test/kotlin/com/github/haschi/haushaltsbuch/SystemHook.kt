package com.github.haschi.haushaltsbuch

import com.github.haschi.haushaltsbuch.infrastruktur.RestApi
import com.github.haschi.haushaltsbuch.infrastruktur.Testcloud
import cucumber.api.Scenario
import cucumber.api.java.After
import cucumber.api.java.Before

class SystemHook(val cloud: Testcloud)
{
    @Before("@system")
    fun beforeScenario(scenario: Scenario)
    {
        cloud.verticleSynchronBereitstellen(RestApi())
    }

    @After("@system")
    fun afterScenario(scenario: Scenario) {
    }
}