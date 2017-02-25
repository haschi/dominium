package com.github.haschi.haushaltsbuch.rest;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.UUID;

@Path("prozessstatus")
public class Prozessstatus
{

    @Inject
    private JobService jobService;

    @GET
    @Path("{id}")
    @Produces(MediaType.APPLICATION_JSON)

    public Response status(@PathParam("id") final UUID id)
    {
        return this.jobService.status(id);
    }
}
