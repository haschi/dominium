package com.github.haschi.haushaltsbuch.api.kommando;

import com.github.haschi.modeling.de.Anweisung;
import org.axonframework.commandhandling.annotation.TargetAggregateIdentifier;

import java.util.UUID;

@Anweisung("Beginne Haushaltsbuchführung")
public interface BeginneHaushaltsbuchfuehrung
{

    @TargetAggregateIdentifier
    UUID id();
}
