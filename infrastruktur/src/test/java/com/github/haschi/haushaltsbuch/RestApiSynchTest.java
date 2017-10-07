package com.github.haschi.haushaltsbuch;

import com.github.haschi.haushaltsbuch.infrastruktur.RestApi;
import io.restassured.response.Response;
import io.vertx.core.Future;
import io.vertx.core.Vertx;
import io.vertx.ext.unit.Async;
import io.vertx.ext.unit.TestContext;
import io.vertx.ext.unit.junit.VertxUnitRunner;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import static io.restassured.RestAssured.when;
import static org.hamcrest.CoreMatchers.equalTo;

@RunWith(VertxUnitRunner.class)
public class RestApiSynchTest
{
    private Vertx vertx;

    @Before
    public void setup(final TestContext tc) {
        vertx = Vertx.vertx();
        vertx.exceptionHandler(tc.exceptionHandler());
        vertx.deployVerticle(RestApi.class.getName(), tc.asyncAssertSuccess());
    }

    @After
    public void tearDown(final TestContext tc) throws InterruptedException
    {
        vertx.close(tc.asyncAssertSuccess());
    }

    @Test
    public void testThatTheServiceResponses(final TestContext tc) {
        final Async async = tc.async();
        vertx.executeBlocking((Future<Response> future) -> {
            final Response response = when().get("http://localhost:8080");
            future.complete(response);

        }, result -> {
            result.result().then()
                    .statusCode(200)
                    .body(equalTo("infrastruktur 0.0.1-SNAPSHOT"));
            async.complete();
        });
    }

    @Test
    public void testThatDefaultsAreOk() {
        when().get("/")
        .then().body(equalTo("infrastruktur 0.0.1-SNAPSHOT"));
    }
}
