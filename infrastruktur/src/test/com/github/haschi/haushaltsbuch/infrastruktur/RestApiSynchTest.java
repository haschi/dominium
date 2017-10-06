package com.github.haschi.haushaltsbuch.infrastruktur;

import io.vertx.core.Vertx;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static io.restassured.RestAssured.when;
import static org.hamcrest.CoreMatchers.equalTo;

public class RestApiSynchTest
{
    private Vertx vertx;

    @Before
    public void setup() {
        vertx = Vertx.vertx();
        vertx.deployVerticle(RestApi.class.getName());
    }

    @After
    public void tearDown() throws InterruptedException
    {
        vertx.close();
        Thread.sleep(1000);
    }

    @Test
    public void testThatTheServiceResponses() {
        when().get("http://localhost:8080")
                .then().statusCode(200);
    }

    @Test
    public void testThatDefaultsAreOk() {
        when().get("/")
        .then().body(equalTo("Hello Vert.x!"));
    }
}
