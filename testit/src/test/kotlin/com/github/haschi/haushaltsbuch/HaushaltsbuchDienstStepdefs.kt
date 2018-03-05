package com.github.haschi.haushaltsbuch

import com.github.haschi.haushaltsbuch.backend.Application
import cucumber.api.java.de.Wenn
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.ContextConfiguration

@SpringBootTest(classes = [Application::class])
@ContextConfiguration
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
