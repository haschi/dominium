package com.github.haschi.haushaltsbuch.api.kommando;

import org.axonframework.commandhandling.annotation.TargetAggregateIdentifier;
import org.immutables.value.Value;

import java.util.UUID;

@Value.Immutable
public interface BeginneHaushaltsbuchfuehrung {

    @TargetAggregateIdentifier
    UUID id();
}
