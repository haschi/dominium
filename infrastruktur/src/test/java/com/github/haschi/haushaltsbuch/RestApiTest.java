package com.github.haschi.haushaltsbuch;

import com.github.haschi.haushaltsbuch.infrastruktur.RestApi;
import io.vertx.core.Vertx;
import io.vertx.ext.unit.Async;
import io.vertx.ext.unit.TestContext;
import io.vertx.ext.unit.junit.VertxUnitRunner;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(VertxUnitRunner.class)
public class RestApiTest
{
    private Vertx vertx;

    @Before
    public void setUp(final TestContext tc) {
        vertx = Vertx.vertx();
        vertx.deployVerticle(RestApi.class.getName(), tc.asyncAssertSuccess());
    }

    @After
    public void tearDown(final TestContext tc) {
        vertx.close(tc.asyncAssertSuccess());
    }

    @Test
    public void testThatTheServerIsStarted(final TestContext tc) {
        final Async async = tc.async();
        vertx.createHttpClient().getNow(8080, "localhost", "/", response -> {
            tc.assertEquals(response.statusCode(), 200);
            response.bodyHandler(body -> {
                tc.assertTrue(body.length() > 0);
                async.complete();
            });
        });
    }
}