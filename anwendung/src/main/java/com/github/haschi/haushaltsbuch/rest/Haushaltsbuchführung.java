package com.github.haschi.haushaltsbuch.rest;

import com.github.haschi.haushaltsbuch.api.kommando.ImmutableBeginneHaushaltsbuchfuehrung;
import org.axonframework.commandhandling.gateway.CommandGateway;

import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.MediaType;
import java.util.UUID;

@Path("buchfuehrug")
public class Haushaltsbuchführung
{
    final CommandGateway commandGateway;

    @Inject
    public Haushaltsbuchführung(final CommandGateway commandcommandGateway)
    {
        this.commandGateway = commandcommandGateway;
    }

    @POST
    @Path("{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    public void haushaltsbuchführungBeginnen(final UUID id)
    {
        this.commandGateway.send(ImmutableBeginneHaushaltsbuchfuehrung.builder().id(id).build());
    }
}
