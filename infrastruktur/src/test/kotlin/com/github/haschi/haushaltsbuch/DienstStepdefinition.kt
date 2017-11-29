package com.github.haschi.haushaltsbuch

import com.github.haschi.haushaltsbuch.infrastruktur.Welt
import cucumber.api.java.de.Dann
import cucumber.api.java.de.Und
import cucumber.api.java.de.Wenn
import io.restassured.RestAssured.`when`
import org.assertj.core.api.Assertions.assertThat
import org.hamcrest.Matchers.equalTo

class DienstStepdefinition(private val welt: Welt)
{
    @Wenn("^ich den Haushaltsbuchdienst starte$")
    @Throws(Throwable::class)
    fun ichDenHaushaltsbuchdienstStarte()
    {
        // cloud.deployVerticle(RestApi::class.java)
        // Thread.sleep(1000)
    }

    @Und("^ich die Version abfrage$")
    @Throws(Throwable::class)
    fun ichDieVersionAbfrage()
    {
        val response = `when`().get("/")
        welt.letzteAntwort = response

    }

    @Dann("^werde ich die Version \"([^\"]*)\" erhalten$")
    fun werdeIchDieVersionErhalten(version: String)
    {
        assertThat(welt.letzteAntwort).isNotNull()
        welt.letzteAntwort!!.then()
            .assertThat()
            .statusCode(200).body(equalTo(version))
    }
}
