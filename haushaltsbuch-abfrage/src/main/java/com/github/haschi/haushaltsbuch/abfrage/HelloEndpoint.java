package com.github.haschi.haushaltsbuch.abfrage;

import javax.enterprise.context.ApplicationScoped;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;

@ApplicationScoped
@Path("/hello")
public class HelloEndpoint {

//    @Inject
//    private Configuration konfiguration;

    @GET
    @Produces("text/plain")
    public Response doGet() {
        return Response.ok("Hello from Haushaltsbuch query").build();
    }

    @GET
    @Produces("text/plain")
    @Path("world")
    public Response getWorld() {
//        return Response.ok(
//                konfiguration.getModules()
//                    .toArray()
//        ).build();

        return Response.ok().build();
    }
}
