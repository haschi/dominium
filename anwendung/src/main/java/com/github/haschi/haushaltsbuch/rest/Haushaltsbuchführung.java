package com.github.haschi.haushaltsbuch.rest;

import com.github.haschi.haushaltsbuch.abfragen.HauptbuchAbfrage;
import com.github.haschi.haushaltsbuch.abfragen.HauptbuchAnsicht;
import com.github.haschi.haushaltsbuch.api.kommando.ImmutableBeginneHaushaltsbuchführung;
import com.github.haschi.haushaltsbuch.infrastruktur.Job;
import org.axonframework.commandhandling.gateway.CommandGateway;

import javax.annotation.Resource;
import javax.enterprise.concurrent.ContextService;
import javax.enterprise.concurrent.ManagedExecutorService;
import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import java.util.UUID;

@Path("hauptbuch")
public class Haushaltsbuchführung
{
    @Inject
    private CommandGateway befehlsbrücke;

    @Inject
    HauptbuchAbfrage abfrage;

    @POST
    @Path("xxx/{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    public void haushaltsbuchführungBeginnen(@PathParam("id") final UUID id)
    {
        this.befehlsbrücke.send(
                ImmutableBeginneHaushaltsbuchführung.builder().id(id).build());
    }

    @Resource
    private ContextService contextService;

    @Resource
    private ManagedExecutorService executor;

    @Inject
    private JobService jobService;

    @POST
    @Path("haushaltsbuchanlage")
    public Response haushaltsbuchanlage(@Context final UriInfo uriInfo)
    {
        final Job job = this.jobService.neuenJobErzeugen(uriInfo);
        //
        final Haushaltsbuchanlage h = new Haushaltsbuchanlage(
                this.befehlsbrücke, job);

        this.executor.execute(h);
        return Response.accepted()
                .location(job.location(uriInfo))
                .header("Access-Control-Expose-Headers", "Location")
                .build();
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
