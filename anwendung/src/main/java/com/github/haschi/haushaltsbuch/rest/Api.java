package com.github.haschi.haushaltsbuch.rest;

import com.strategicgains.hyperexpress.HyperExpress;
import com.strategicgains.hyperexpress.domain.Resource;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/")
public class Api
{
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response service()
    {
        final ApiInfo info = ImmutableApiInfo.builder()
                .name("haushaltsbuch")
                .version(1)
                .build();

        final Resource resource = HyperExpress.createResource(info, "application/json");

        return Response.ok()
                .entity(resource)
                .build();
    }

    @POST
    @Path("haushaltsbucheröffnung")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response haushaltsbucheröffnung()
    {
        return null;
    }
}
