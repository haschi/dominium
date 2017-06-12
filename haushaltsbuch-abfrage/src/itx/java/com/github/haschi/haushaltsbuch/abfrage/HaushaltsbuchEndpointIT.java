//package com.github.haschi.haushaltsbuch.abfrage;
//
//import org.jboss.arquillian.container.test.api.RunAsClient;
//import org.jboss.arquillian.junit.Arquillian;
//import org.jboss.arquillian.test.api.ArquillianResource;
//import org.junit.Test;
//import org.junit.runner.RunWith;
//import org.wildfly.swarm.arquillian.DefaultDeployment;
//
//import javax.naming.NamingException;
//import java.net.URL;
//import java.util.UUID;
//
//import static io.restassured.RestAssured.get;
//
//@RunWith(Arquillian.class)
//@DefaultDeployment
//public class HaushaltsbuchEndpointIT
//{
//    @ArquillianResource
//    private URL baseUrl;
//
//    @Test
//    @RunAsClient
//    public void testHelloWithRest() throws NamingException
//    {
//        final UUID identifier = UUID.randomUUID();
//        get(baseUrl + "haushaltsbuch/" + identifier)
//                .then()
//                .statusCode(200);
//    }
//}
