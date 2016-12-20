package com.github.haschi.haushaltsbuch.api.kommando;

import com.github.haschi.modeling.de.Anweisung;
import org.axonframework.commandhandling.annotation.TargetAggregateIdentifier;

import java.util.UUID;

@Anweisung("Beginne Haus&shy;halts&shy;buch&shy;füh&shy;rung")
public interface BeginneHaushaltsbuchführung
{
    @TargetAggregateIdentifier
    UUID id();
}
