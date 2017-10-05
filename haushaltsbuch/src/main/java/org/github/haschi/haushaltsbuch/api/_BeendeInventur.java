package org.github.haschi.haushaltsbuch.api;

import org.axonframework.commandhandling.TargetAggregateIdentifier;
import org.github.haschi.haushaltsbuch.infrastruktur.modellierung.de.Aggregatkennung;
import org.github.haschi.haushaltsbuch.infrastruktur.modellierung.de.Anweisung;
import org.immutables.value.Value;

@Anweisung
public interface _BeendeInventur
{
    @Value.Parameter
    @TargetAggregateIdentifier
    Aggregatkennung von();
}
