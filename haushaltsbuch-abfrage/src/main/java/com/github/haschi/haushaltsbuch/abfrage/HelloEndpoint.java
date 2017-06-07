package com.github.haschi.haushaltsbuch.abfrage;

import org.jgroups.JChannel;
import org.jgroups.blocks.atomic.Counter;
import org.jgroups.blocks.atomic.CounterService;

import javax.annotation.Resource;
import javax.enterprise.context.ApplicationScoped;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;
import java.text.MessageFormat;

@ApplicationScoped
@Path("/hello")
public class HelloEndpoint {

//    @Inject
//    private Configuration konfiguration;

    @Resource(lookup = "java:jboss/jgroups/channel/haushaltsbuch-jgroups")
    // @Resource()
    private JChannel channel;

    @GET
    @Produces("text/plain")
    public Response doGet() {
        return Response.ok("Hello from Haushaltsbuch query").build();
    }

    @GET
    @Produces("text/plain")
    @Path("world")
    public Response getWorld() throws Exception
    {
        CounterService counter_service = new CounterService(channel);
        channel.connect("haushaltsbuch-jgroups");
        Counter counter = counter_service.getOrCreateCounter("mycounter", 1);

        return Response.ok(
                MessageFormat.format("Wert: {0}",
                                     counter.incrementAndGet()))
                .build();
    }
}
