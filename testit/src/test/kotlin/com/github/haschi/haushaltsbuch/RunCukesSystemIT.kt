package com.github.haschi.haushaltsbuch

import cucumber.api.CucumberOptions
import cucumber.api.junit.Cucumber
import org.junit.runner.RunWith

@RunWith(Cucumber::class)
@CucumberOptions(strict = true, tags = ["@system"], plugin = ["pretty"])
class RunCukesSystemIT
