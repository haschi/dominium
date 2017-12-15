package com.github.haschi.haushaltsbuch

import com.github.haschi.haushaltsbuch.infrastruktur.RestApi
import com.github.haschi.haushaltsbuch.infrastruktur.Testcloud
import com.github.haschi.haushaltsbuch.infrastruktur.Welt
import cucumber.api.java.de.Dann
import cucumber.api.java.de.Und
import cucumber.api.java.de.Wenn
import io.restassured.RestAssured.`when`
import io.vertx.kotlin.core.json.json
import io.vertx.kotlin.core.json.obj
import org.assertj.core.api.Assertions.assertThat
import org.hamcrest.Matchers.equalTo

class DienstStepdefinition(private val welt: Welt, private val cloud: Testcloud)
{
    @Wenn("^ich den Haushaltsbuchdienst starte$")
    @Throws(Throwable::class)
    fun ichDenHaushaltsbuchdienstStarte()
    {
        cloud.verticleSynchronBereitstellen(RestApi())
    }

    @Und("^ich die Version abfrage$")
    @Throws(Throwable::class)
    fun ichDieVersionAbfrage()
    {
        welt.letzteAntwort = `when`().get("/version")
    }

    @Dann("^werde ich die Version \"([^\"]*)\" \"([^\"]*)\" erhalten$")
    fun werdeIchDieVersionErhalten(name: String, version: String)
    {
        assertThat(welt.letzteAntwort).isNotNull()
        welt.letzteAntwort!!.then()
            .assertThat()
                .statusCode(200)
                .body(equalTo( json {
                    obj ("name" to name, "version" to version)
                }.encode()))
    }
}
