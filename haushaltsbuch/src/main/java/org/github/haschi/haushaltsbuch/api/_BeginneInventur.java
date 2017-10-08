package org.github.haschi.haushaltsbuch.api;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import org.axonframework.commandhandling.TargetAggregateIdentifier;
import org.github.haschi.haushaltsbuch.infrastruktur.modellierung.de.Aggregatkennung;
import org.github.haschi.haushaltsbuch.infrastruktur.modellierung.de.Anweisung;
import org.immutables.value.Value;

@Anweisung
@JsonSerialize(as = BeginneInventur.class)
@JsonDeserialize(as = BeginneInventur.class)
public interface _BeginneInventur
{
    @TargetAggregateIdentifier
    @Value.Parameter
    Aggregatkennung id();
}
