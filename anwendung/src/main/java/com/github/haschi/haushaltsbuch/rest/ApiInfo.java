package com.github.haschi.haushaltsbuch.rest;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.github.haschi.modeling.de.Information;

@Information
@JsonSerialize(as = ImmutableApiInfo.class)
@JsonDeserialize(as = ImmutableApiInfo.class)
public interface ApiInfo
{
    String name();

    Integer version();
}
