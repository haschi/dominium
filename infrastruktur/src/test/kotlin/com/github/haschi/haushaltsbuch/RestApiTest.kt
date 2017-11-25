package com.github.haschi.haushaltsbuch

import com.github.haschi.haushaltsbuch.infrastruktur.RestApi
import io.vertx.core.Vertx
import io.vertx.core.eventbus.DeliveryOptions
import io.vertx.core.json.Json
import io.vertx.core.json.JsonObject
import io.vertx.ext.unit.Async
import io.vertx.ext.unit.TestContext
import io.vertx.ext.unit.junit.VertxUnitRunner
import org.github.haschi.haushaltsbuch.api.BeginneInventur
import org.github.haschi.haushaltsbuch.infrastruktur.modellierung.de.Aggregatkennung
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(VertxUnitRunner::class)
class RestApiTest
{
    private var vertx: Vertx? = null

    @Before
    fun setUp(tc: TestContext)
    {
        vertx = Vertx.vertx()
        vertx!!.deployVerticle(RestApi::class.java.name, tc.asyncAssertSuccess())
    }

    @After
    fun tearDown(tc: TestContext)
    {
        vertx!!.close(tc.asyncAssertSuccess())
    }

    @Test
    fun testThatTheServerIsStarted(tc: TestContext)
    {
        val async = tc.async()
        vertx!!.createHttpClient().getNow(8080, "localhost", "/") { response ->
            tc.assertEquals(response.statusCode(), 200)
            response.bodyHandler { body ->
                tc.assertTrue(body.length() > 0)
                async.complete()
            }
        }
    }
}