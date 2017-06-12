//package com.github.haschi.haushaltsbuch.abfrage;
//
//import org.jboss.arquillian.container.test.api.RunAsClient;
//import org.jboss.arquillian.drone.api.annotation.Drone;
//import org.jboss.arquillian.junit.Arquillian;
//import org.jboss.arquillian.test.api.ArquillianResource;
//import org.junit.Test;
//import org.junit.runner.RunWith;
//import org.openqa.selenium.WebDriver;
//import org.wildfly.swarm.arquillian.DefaultDeployment;
//
//import java.net.URL;
//
//import static io.restassured.RestAssured.get;
//import static org.assertj.core.api.Assertions.assertThat;
//import static org.hamcrest.CoreMatchers.equalTo;
//
//@RunWith(Arquillian.class)
//@DefaultDeployment(testable = false)
//@RunAsClient
//public class HelloEndpointIT
//{
//    @Drone
//    private WebDriver browser;
//
//    @ArquillianResource(HelloEndpoint.class)
//    private URL basisUrl;
//
//    @Test
//    public void testHelloWithBrowser()
//    {
//        browser.navigate().to(basisUrl + "hello");
//
//        assertThat(browser.getPageSource()).contains("Hello from Haushaltsbuch query");
//    }
//
//    @Test
//    public void testEndpointUrl() {
//        assertThat(basisUrl.toExternalForm()).isEqualTo("http://localhost:8080/");
//    }
//
//    @Test
//    public void testHelloWithRest() {
//        get(basisUrl + "hello")
//                .then()
//                .statusCode(200)
//                .body(equalTo("Hello from Haushaltsbuch query"));
//    }
//}
