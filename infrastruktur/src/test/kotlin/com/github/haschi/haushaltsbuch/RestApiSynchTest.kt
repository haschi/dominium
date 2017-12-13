package com.github.haschi.haushaltsbuch

import com.github.haschi.dominium.haushaltsbuch.core.model.commands.BeginneInventur
import com.github.haschi.dominium.haushaltsbuch.core.model.values.Aggregatkennung
import com.github.haschi.dominium.haushaltsbuch.core.model.values.Inventar
import com.github.haschi.haushaltsbuch.infrastruktur.RestApi
import io.restassured.RestAssured.`when`
import io.vertx.ext.unit.TestContext
import io.vertx.ext.unit.junit.VertxUnitRunner
import io.vertx.reactivex.core.Vertx
import io.vertx.reactivex.ext.web.client.WebClient
import org.hamcrest.Matchers.equalTo
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(VertxUnitRunner::class)
class RestApiSynchTest
{
    private var vertx: Vertx? = null

    @Before
    fun setup(tc: TestContext)
    {
        vertx = Vertx.vertx()
        vertx!!.exceptionHandler(tc.exceptionHandler())
        vertx!!.deployVerticle(RestApi::class.java.name, tc.asyncAssertSuccess())
    }

    @After
    @Throws(InterruptedException::class)
    fun tearDown(tc: TestContext)
    {
        vertx!!.close(tc.asyncAssertSuccess())
    }

    @Test
    fun testThatDefaultsAreOk()
    {
        `when`().get("/")
                .then().body(equalTo("haushaltsbuch-backend CD-SNAPSHOT"))
    }

    @Test
    fun testThatJsonBodyCanBeMergedWithPathParameters(tc: TestContext)
    {
        val async = tc.async()
        val client = WebClient.create(vertx!!)
        val aggregatkennung = Aggregatkennung.neu()


        client.post(8080, "localhost", "/gateway/inventar")
                .rxSendJson(BeginneInventur(aggregatkennung))
                .flatMap {
                    val location = it.getHeader("Location")

                    // Anstelle der erwarteten Anweisung ErfasseInventar schickt der
                    // Client das Inventar an die Resource. Der Server setzt die
                    // Anweisung aus den Pfadparametern und dem JSON Body selbst
                    // zusammen.
                    client.post(8080, "localhost", location)
                            .rxSendJson(Inventar.leer)
                }
                .subscribe(
                        {
                            tc.assertEquals(it.statusCode(), 201)
                            async.complete()
                        },
                        {
                            tc.fail(it.message)
                        })
    }
}
