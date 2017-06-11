package com.github.haschi.haushaltsbuch.abfrage;

import org.jboss.arquillian.container.test.api.RunAsClient;
import org.jboss.arquillian.drone.api.annotation.Drone;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.arquillian.test.api.ArquillianResource;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.openqa.selenium.WebDriver;
import org.wildfly.swarm.Swarm;
import org.wildfly.swarm.arquillian.CreateSwarm;
import org.wildfly.swarm.arquillian.DefaultDeployment;

import java.net.URL;
import java.text.MessageFormat;

import static io.restassured.RestAssured.get;
import static org.hamcrest.CoreMatchers.equalTo;

@RunWith(Arquillian.class)
@DefaultDeployment
public class HelloEndpointIT
{
    @Drone
    private WebDriver browser;

    @ArquillianResource
    private URL path;

    @CreateSwarm
    public static Swarm startServer() throws Exception
    {
        return Main.createSwarm("-Dswarm.bind.address=127.0.0.1");
    }

    @Test
    @RunAsClient
    public void testHelloWithBrowser() {
        System.out.print(MessageFormat.format("Arquillian Resource PATH: {0}", path.toString()));

//        browser.navigate().to("http://localhost:8080/hello");
//        assertThat(browser.getPageSource()).contains("Hello from Haushaltsbuch query");
    }

    @Test
    @RunAsClient
    public void testHelloWithRest() {
        get("http://localhost:8080/hello")
                .then()
                .statusCode(200)
                .body(equalTo("Hello from Haushaltsbuch query"));
    }
}
