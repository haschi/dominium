package org.github.haschi.haushaltsbuch;

import cucumber.api.CucumberOptions;
import cucumber.api.junit.Cucumber;
import org.junit.runner.RunWith;

@CucumberOptions(
        strict = true,
        plugin = {
                "pretty",
                "html:target/site/cucumber-integration-pretty",
                "json:target/cucumber/integration.json",
                "junit:target/cucumber/integration.xml"},
        tags = {"~@ignore"})
@RunWith(Cucumber.class)
public class RunCukesTest
{
}

