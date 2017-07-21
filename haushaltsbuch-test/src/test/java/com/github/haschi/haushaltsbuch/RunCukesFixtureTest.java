package com.github.haschi.haushaltsbuch;

import cucumber.api.CucumberOptions;
import cucumber.api.junit.Cucumber;
import org.junit.runner.RunWith;

@RunWith(Cucumber.class)
@CucumberOptions(tags = {"@fixture", "~@ignore"}, strict = true, plugin = {"pretty", "default_summary"})
public class RunCukesFixtureTest
{
}
