package com.github.haschi.haushaltsbuch.rest;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/hallo")
public class Hello
{
    @GET
    @Produces(MediaType.TEXT_PLAIN)
    public Response hallo()
    {
        return Response.ok("Hallo Welt").build();
    }
}
