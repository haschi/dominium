package com.github.haschi.haushaltsbuch.api;

import com.github.haschi.modeling.de.Anweisung;
import org.axonframework.commandhandling.TargetAggregateIdentifier;

import java.util.UUID;

@Anweisung("Beginne Haus&shy;halts&shy;buch&shy;füh&shy;rung")
public interface BeginneHaushaltsbuchführung
{
    @TargetAggregateIdentifier
    UUID id();
}
