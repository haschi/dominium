package domaene;

import cucumber.api.CucumberOptions;
import cucumber.api.junit.Cucumber;
import org.junit.runner.RunWith;

@CucumberOptions(monochrome = false,
        strict = false,
        plugin = {"pretty:target/cucumber/integration.txt", "html:target/site/cucumber-integration-pretty",
                "json:target/cucumber/integration.json", "junit:target/cucumber/integration.xml"},
        tags = {"~@ignore", "@integration"})
@RunWith(Cucumber.class)
public class Integrationstests
{
}

