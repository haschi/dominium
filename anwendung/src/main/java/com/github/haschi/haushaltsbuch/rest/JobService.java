package com.github.haschi.haushaltsbuch.rest;

import com.github.haschi.haushaltsbuch.infrastruktur.Job;

import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

public class JobService
{
    static private final Map<UUID, Job> cache = new ConcurrentHashMap<>();

    public Job neuenJobErzeugen(final UriInfo uriInfo)
    {
        final UUID id = UUID.randomUUID();
        final Job job = new Job(id, uriInfo);
        cache.put(id, job);

        return job;
    }

    public Response status(final UUID id)
    {
        if (cache.containsKey(id))
        {
            return cache.get(id).status();
        }

        return Response.status(Response.Status.BAD_REQUEST).build();
    }
}
