package com.github.haschi.haushaltsbuch.api;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.github.haschi.modeling.de.Ereignis;

import java.util.UUID;

@Ereignis
@JsonSerialize(as = ImmutableHaushaltsbuchführungBegonnen.class)
@JsonDeserialize(as = ImmutableHaushaltsbuchführungBegonnen.class)
public interface HaushaltsbuchführungBegonnen
{
    UUID id();
}
