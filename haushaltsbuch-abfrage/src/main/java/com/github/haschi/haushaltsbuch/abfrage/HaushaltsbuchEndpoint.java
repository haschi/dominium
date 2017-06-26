package com.github.haschi.haushaltsbuch.abfrage;



import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.UUID;

@Path("/haushaltsbuch")
//@Advertise("haushaltsbuch")
public class HaushaltsbuchEndpoint
{
    @GET
    @Path("{identifier}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response get(@PathParam("identifier")UUID identifier) {
        final ImmutableHaushaltsbuch build = ImmutableHaushaltsbuch.builder().build();
        return Response.ok(build).build();
    }
}
