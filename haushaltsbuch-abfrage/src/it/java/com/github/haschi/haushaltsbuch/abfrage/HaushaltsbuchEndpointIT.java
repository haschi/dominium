package com.github.haschi.haushaltsbuch.abfrage;

import org.jboss.arquillian.container.test.api.RunAsClient;
import org.jboss.arquillian.junit.Arquillian;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.wildfly.swarm.Swarm;
import org.wildfly.swarm.arquillian.CreateSwarm;
import org.wildfly.swarm.arquillian.DefaultDeployment;

import javax.naming.NamingException;
import java.util.UUID;

import static io.restassured.RestAssured.get;

@RunWith(Arquillian.class)
@DefaultDeployment
public class HaushaltsbuchEndpointIT
{
    @CreateSwarm
    public static Swarm startServer() throws Exception
    {
        return Main.createSwarm("-Dswarm.bind.address=127.0.0.1");
    }

    @Test
    @RunAsClient
    public void testHelloWithRest() throws NamingException
    {
//        final Topology lookup = Topology.lookup();
//        final List<Topology.Entry> haushaltsbuch = lookup.asMap().get("haushaltsbuch");
//        assertThat(haushaltsbuch).isNotEmpty();

        final UUID identifier = UUID.randomUUID();
        get("http://localhost:8080/haushaltsbuch/" + identifier)
                .then()
                .statusCode(200);
    }
}
