package com.github.haschi.haushaltsbuch

import com.github.haschi.dominium.haushaltsbuch.core.model.commands.BeendeInventur
import cucumber.api.PendingException
import cucumber.api.java.de.Wenn
import org.springframework.boot.test.context.SpringBootTest

// @SpringBootTest(classes = [BeendeInventur::class])
class HaushaltsbuchDienstStepdefs
{
    @Wenn("^ich den Haushaltsbuchdienst starte$")
    @Throws(Throwable::class)
    fun ichDenHaushaltsbuchdienstStarte()
    {
        // Write code here that turns the phrase above into concrete actions
        //throw PendingException()
    }
}
