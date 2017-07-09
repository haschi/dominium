package com.github.haschi.haushaltsbuch.api;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.github.haschi.modeling.de.Anweisung;
import org.axonframework.commandhandling.TargetAggregateIdentifier;

import java.util.UUID;

@Anweisung("Beginne Haus&shy;halts&shy;buch&shy;füh&shy;rung")
@JsonSerialize(as = ImmutableBeginneHaushaltsbuchführung.class)
@JsonDeserialize(as = ImmutableBeginneHaushaltsbuchführung.class)
public interface BeginneHaushaltsbuchführung
{
    @TargetAggregateIdentifier
    UUID id();
}
