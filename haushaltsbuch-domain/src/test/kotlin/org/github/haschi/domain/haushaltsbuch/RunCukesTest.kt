package org.github.haschi.domain.haushaltsbuch

import cucumber.api.CucumberOptions
import cucumber.api.junit.Cucumber
import cucumber.deps.com.thoughtworks.xstream.annotations.XStreamConverter
import org.github.haschi.infrastruktur.MoneyConverter
import org.junit.runner.RunWith

@XStreamConverter(MoneyConverter::class)
@CucumberOptions(tags = ["not @ignore"])//        strict = true,
//
//        plugin = {
//                "pretty",
//                "html:target/site/cucumber-integration-pretty",
//                "json:target/cucumber/integration.json",
//                "junit:target/cucumber/integration.xml"},
@RunWith(Cucumber::class)
class RunCukesTest

