package com.github.haschi.domain.haushaltsbuch

import cucumber.api.CucumberOptions
import cucumber.api.junit.Cucumber
import cucumber.deps.com.thoughtworks.xstream.annotations.XStreamConverter
import com.github.haschi.domain.haushaltsbuch.testing.MoneyConverter
import org.junit.runner.RunWith

@XStreamConverter(MoneyConverter::class)
@CucumberOptions(
        strict = true,
        tags = ["@domain"],
        plugin = ["progress"])
@RunWith(Cucumber::class)
class RunCukesTest

