package com.github.haschi.haushaltsbuch


// import io.vertx.ext.web.client.WebClient
import com.github.haschi.domain.haushaltsbuch.modell.core.commands.BeginneInventur
import com.github.haschi.domain.haushaltsbuch.modell.core.commands.ErfasseInventar
import com.github.haschi.domain.haushaltsbuch.modell.core.values.Aggregatkennung
import com.github.haschi.domain.haushaltsbuch.modell.core.values.Inventar
import com.github.haschi.haushaltsbuch.infrastruktur.RestApi
import io.restassured.RestAssured.`when`
import io.vertx.ext.unit.TestContext
import io.vertx.ext.unit.junit.VertxUnitRunner
import io.vertx.reactivex.core.Vertx
import io.vertx.reactivex.ext.web.client.WebClient
import org.hamcrest.CoreMatchers.equalTo
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
    fun testThatTheServiceResponses(tc: TestContext)
    {
//        val async = tc.async()
//        vertx!!.executeBlocking({ future: Future<Response> ->
//            val response = `when`().get("http://localhost:8080")
//            future.complete(response)
//
//        }) { result ->
//            result.result().then()
//                    .statusCode(200)
//                    .body(equalTo("infrastruktur 0.0.1-SNAPSHOT"))
//            async.complete()
//        }
    }

    @Test
    fun testThatDefaultsAreOk()
    {
        `when`().get("/")
                .then().body(equalTo("infrastruktur 0.0.1-SNAPSHOT"))
    }

    @Test
    fun testThatJsonBodyCanBeMergedWithPathParameters(tc: TestContext)
    {
        val async = tc.async();
        val client = WebClient.create(vertx!!)
        val aggregatkennung = Aggregatkennung.neu()


        client.post(8080, "localhost", "/api/inventar")
                .rxSendJson(BeginneInventur(aggregatkennung))
                .flatMap {
                    val location = it.getHeader("Location")
                    val inventurId = Aggregatkennung.aus(it.getHeader("AggregatId"))
                    client.post(8080, "localhost", location)
                            .rxSendJson(ErfasseInventar(inventurId, Inventar.leer))
                }
                .subscribe({ async.complete() },
                        { tc.fail(it.message) })
    }
}
