package com.github.haschi.haushaltsbuch

import com.github.haschi.haushaltsbuch.infrastruktur.RestApi
import com.github.haschi.haushaltsbuch.infrastruktur.Testcloud
import com.github.haschi.haushaltsbuch.infrastruktur.Welt
import cucumber.api.PendingException
import cucumber.api.java.de.Dann
import cucumber.api.java.de.Und
import cucumber.api.java.de.Wenn
import io.restassured.response.Response

import io.restassured.RestAssured.`when`
import org.hamcrest.Matchers.equalTo

class DienstStepdefinition(private val cloud: Testcloud, private val welt: Welt)
{

    @Wenn("^ich den Haushaltsbuchdienst starte$")
    @Throws(Throwable::class)
    fun ichDenHaushaltsbuchdienstStarte()
    {
        cloud.deployVerticle(RestApi::class.java)
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
        welt.letzteAntwort!!.then().statusCode(200).body(equalTo(version))
    }
}
