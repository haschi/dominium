package com.github.haschi.haushaltsbuch.rest;

import com.fasterxml.jackson.annotation.JsonView;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.github.haschi.modeling.de.Information;

import javax.ws.rs.core.Link;
import java.net.URI;

@Information
@JsonSerialize(as = ImmutableHalLink.class)
public interface HalLink
{
    @JsonView()
    String rel();

    URI href();

    default HalLink from(final Link link)
    {
        return null;
    }
}
