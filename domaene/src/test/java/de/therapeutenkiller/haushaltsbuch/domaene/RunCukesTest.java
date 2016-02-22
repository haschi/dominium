package de.therapeutenkiller.haushaltsbuch.domaene;

import cucumber.api.CucumberOptions;
import cucumber.api.junit.Cucumber;
import org.junit.runner.RunWith;

@CucumberOptions(
    monochrome = true,
    strict = true,
    plugin = {"pretty", "html:target/site/cucumber-pretty", "json:target/cucumber.json"},
    tags = {"~@ignore"})
@RunWith(Cucumber.class)
public class RunCukesTest {
}
