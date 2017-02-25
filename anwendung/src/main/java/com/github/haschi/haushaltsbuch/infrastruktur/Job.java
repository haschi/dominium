package com.github.haschi.haushaltsbuch.infrastruktur;

import com.github.haschi.haushaltsbuch.rest.Prozessstatus;

import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import java.net.URI;
import java.util.UUID;

public class Job
{
    private final UriInfo uriInfo;
    private final UUID id;
    private Response response = Response.accepted().build();

    public Job(final UUID id, final UriInfo uriInfo)
    {
        this.id = id;
        this.uriInfo = uriInfo;
    }

    public URI location(final UriInfo uriInfo)
    {
        return uriInfo.getBaseUriBuilder()
                .path(Prozessstatus.class)
                .path(Prozessstatus.class, "status")
                .build(id());
    }

    public UUID id()
    {
        return this.id;
    }

    public void resume(final Response build)
    {
        this.response = build;
    }

    public Response status()
    {
        return this.response;
    }
}
