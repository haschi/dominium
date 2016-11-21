package com.github.haschi.haushaltsbuch.rest;

import com.github.haschi.haushaltsbuch.abfragen.HauptbuchAbfrage;
import com.github.haschi.haushaltsbuch.abfragen.HauptbuchAnsicht;
import com.github.haschi.haushaltsbuch.api.kommando.ImmutableBeginneHaushaltsbuchfuehrung;
import org.axonframework.commandhandling.gateway.CommandGateway;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.UUID;

@Path("hauptbuch")
public class Haushaltsbuchführung
{
    @Inject
    private CommandGateway commandGateway;

    @Inject
    HauptbuchAbfrage abfrage;

    @POST
    @Path("{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    public void haushaltsbuchführungBeginnen(@PathParam("id") final UUID id)
    {
        this.commandGateway.send(ImmutableBeginneHaushaltsbuchfuehrung.builder().id(id).build());
    }

    @GET
    @Path("{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response haushaltsbuchLesen(@PathParam("id") final UUID id)
    {
        final HauptbuchAnsicht ansicht = this.abfrage.abfragen(id);

        return Response.ok(ansicht).build();
    }
}
