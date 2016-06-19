package com.github.haschi.haushaltsbuch.domaene;

import cucumber.api.CucumberOptions;
import cucumber.api.junit.Cucumber;
import org.junit.runner.RunWith;

@CucumberOptions(
    monochrome = true,
    strict = false,
    plugin = {"pretty", "html:target/site/cucumber-pretty", "json:target/cucumber.json"},
    tags = "~@ignore")
@RunWith(Cucumber.class)
public class RunCukesTest {
}
