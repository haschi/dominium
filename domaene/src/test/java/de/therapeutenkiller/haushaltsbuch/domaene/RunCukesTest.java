package de.therapeutenkiller.haushaltsbuch.domaene;

import cucumber.api.CucumberOptions;
import cucumber.api.junit.Cucumber;
import org.junit.runner.RunWith;

/**
 * Created by mhaschka on 20.09.15.
 */
@CucumberOptions(
    monochrome = true,
    strict = false,
    plugin = {"pretty", "html:target/site/cucumber-pretty", "json:target/cucumber.json"},
    tags = {"~@ignore"})
@RunWith(Cucumber.class)
public class RunCukesTest {
}
