package com.github.haschi.haushaltsbuch;

import com.github.haschi.haushaltsbuch.infrastruktur.RestApi;
import com.github.haschi.haushaltsbuch.infrastruktur.Testcloud;
import com.github.haschi.haushaltsbuch.infrastruktur.Welt;
import cucumber.api.PendingException;
import cucumber.api.java.de.Dann;
import cucumber.api.java.de.Und;
import cucumber.api.java.de.Wenn;
import io.restassured.response.Response;

import static io.restassured.RestAssured.when;
import static org.hamcrest.Matchers.equalTo;

public class DienstStepdefinition
{
    private final Testcloud cloud;
    private final Welt welt;

    public DienstStepdefinition(final Testcloud cloud, final Welt welt)
    {
        this.cloud = cloud;
        this.welt = welt;
    }

    @Wenn("^ich den Haushaltsbuchdienst starte$")
    public void ichDenHaushaltsbuchdienstStarte() throws Throwable
    {
        cloud.deployVerticle(RestApi.class);
    }

    @Und("^ich die Version abfrage$")
    public void ichDieVersionAbfrage() throws Throwable
    {
        final Response response = when().get("/");
        welt.letzteAntwort = response;
    }

    @Dann("^werde ich die Version \"([^\"]*)\" erhalten$")
    public void werdeIchDieVersionErhalten(final String version)
    {
        welt.letzteAntwort.then().statusCode(200).body(equalTo(version));
    }
}
