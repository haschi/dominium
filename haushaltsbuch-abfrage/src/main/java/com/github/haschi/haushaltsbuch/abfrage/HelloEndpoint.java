package com.github.haschi.haushaltsbuch.abfrage;

import org.axonframework.config.Configuration;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.ws.rs.Path;
import javax.ws.rs.core.Response;
import javax.ws.rs.GET;
import javax.ws.rs.Produces;

@ApplicationScoped
@Path("/hello")
public class HelloEndpoint {

    @Inject
    private Configuration konfiguration;

    @GET
    @Produces("text/plain")
    public Response doGet() {
        return Response.ok("Hello from Haushaltsbuch query").build();
    }

    @GET
    @Produces("text/plain")
    @Path("world")
    public Response getWorld() {
        return Response.ok(
                konfiguration.getModules()
                    .toArray()
        ).build();
    }
}
