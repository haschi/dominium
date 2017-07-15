package com.github.haschi.haushaltsbuch.abfrage;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.github.haschi.haushaltsbuch.api.Aggregatkennung;
import com.github.haschi.modeling.de.Information;

@Information
@JsonSerialize(as = ImmutableHaushaltsbuch.class)
@JsonDeserialize(as = ImmutableHaushaltsbuch.class)
public interface Haushaltsbuch
{
    Aggregatkennung id();
}
