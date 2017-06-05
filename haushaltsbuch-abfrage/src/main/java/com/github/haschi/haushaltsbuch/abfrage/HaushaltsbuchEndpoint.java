package com.github.haschi.haushaltsbuch.abfrage;

import org.wildfly.swarm.topology.Advertise;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.Response;
import java.util.UUID;

@Path("/haushaltsbuch")
@Advertise("haushaltsbuch")
public class HaushaltsbuchEndpoint
{
    @GET
    @Path("{identifier}")
    public Response get(@PathParam("identifier")UUID identifier) {
        return Response.ok().build();
    }
}
