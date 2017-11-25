package com.github.haschi.haushaltsbuch

import com.github.haschi.haushaltsbuch.infrastruktur.RestApi
import io.restassured.response.Response
import io.vertx.core.Future
import io.vertx.core.Vertx
import io.vertx.ext.unit.Async
import io.vertx.ext.unit.TestContext
import io.vertx.ext.unit.junit.VertxUnitRunner
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

import io.restassured.RestAssured.`when`
import org.hamcrest.CoreMatchers.equalTo

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
    fun testThatTheServiceResponses(tc: TestContext)
    {
        val async = tc.async()
        vertx!!.executeBlocking({ future: Future<Response> ->
            val response = `when`().get("http://localhost:8080")
            future.complete(response)

        }) { result ->
            result.result().then()
                    .statusCode(200)
                    .body(equalTo("infrastruktur 0.0.1-SNAPSHOT"))
            async.complete()
        }
    }

    @Test
    fun testThatDefaultsAreOk()
    {
        `when`().get("/")
                .then().body(equalTo("infrastruktur 0.0.1-SNAPSHOT"))
    }
}
