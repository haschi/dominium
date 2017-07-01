package com.github.haschi.haushaltsbuch.abfrage;

import org.axonframework.config.Configuration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.UUID;

@Path("haushaltsbuch")
public class HaushaltsbuchEndpoint
{
    private final static Logger log = LoggerFactory.getLogger(HaushaltsbuchEndpoint.class);

    @Inject
    private Configuration konfiguration;

    @GET
    @Path("{identifier}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response get(@PathParam("identifier")UUID identifier)
    {
        Haushaltsbuchverzeichnis haushaltsbuchverzeichnis =
                konfiguration.getComponent(Haushaltsbuchverzeichnis.class);

        return haushaltsbuchverzeichnis.suchen(identifier)
                .map(h -> Response.ok(h)
                        .type(MediaType.APPLICATION_JSON)
                        .build())
                .orElseGet(() -> Response.status(404)
                        .entity("Haushaltsbuch nicht vorhanden")
                           .type(MediaType.TEXT_PLAIN)
                           .build());
    }
}
