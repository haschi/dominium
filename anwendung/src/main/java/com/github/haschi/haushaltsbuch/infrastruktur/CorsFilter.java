package com.github.haschi.haushaltsbuch.infrastruktur;

import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerResponseContext;
import javax.ws.rs.container.ContainerResponseFilter;
import javax.ws.rs.ext.Provider;
import java.io.IOException;

@Provider
public class CorsFilter implements ContainerResponseFilter
{
    @Override
    public void filter(
            final ContainerRequestContext containerRequestContext,
            final ContainerResponseContext containerResponseContext)
            throws IOException
    {
        containerResponseContext.getHeaders().putSingle("Access-Control-Allow-Origin", "*");
        containerResponseContext.getHeaders().putSingle("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE");
        final String reqHeader = containerRequestContext.getHeaderString("Access-Control-Request-Headers");
        if (reqHeader != null && reqHeader != "")
        {
            containerResponseContext.getHeaders().putSingle("Access-Control-Allow-Headers", reqHeader);
        }
    }
}
