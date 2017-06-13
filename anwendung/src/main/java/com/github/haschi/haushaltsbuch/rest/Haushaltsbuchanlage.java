package com.github.haschi.haushaltsbuch.rest;

import com.github.haschi.haushaltsbuch.api.ImmutableBeginneHaushaltsbuchführung;
import com.github.haschi.haushaltsbuch.infrastruktur.Job;
import org.axonframework.commandhandling.gateway.CommandGateway;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.UUID;

public class Haushaltsbuchanlage implements Runnable
{
    private final CommandGateway gw;
    private final Job job;

    public Haushaltsbuchanlage(final CommandGateway gw, final Job id)
    {
        this.gw = gw;
        this.job = id;
    }

    @Override
    public void run()
    {
        try
        {
            Thread.sleep(3000);

            final UUID id = UUID.randomUUID();
            this.gw.send(
                    ImmutableBeginneHaushaltsbuchführung.builder()
                            .id(id)
                            .build());

            this.job.resume(Response.ok().build());
        } catch (final Exception e)
        {
            this.job.resume(Response.status(Response.Status.BAD_REQUEST)
                                    .entity(e.getLocalizedMessage())
                                    .type(MediaType.TEXT_PLAIN_TYPE)
                                    .build());
        }
    }
}
