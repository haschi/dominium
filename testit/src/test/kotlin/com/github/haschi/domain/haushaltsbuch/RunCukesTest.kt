package com.github.haschi.domain.haushaltsbuch

import cucumber.api.CucumberOptions
import cucumber.api.junit.Cucumber
import org.junit.runner.RunWith

@CucumberOptions(
        strict = true,
        tags = ["@domain"],
        plugin = ["progress"])
@RunWith(Cucumber::class)
class RunCukesTest

