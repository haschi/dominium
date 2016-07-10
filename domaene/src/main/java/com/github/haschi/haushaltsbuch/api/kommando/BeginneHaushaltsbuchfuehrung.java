package com.github.haschi.haushaltsbuch.api.kommando;

import org.immutables.value.Value;

import java.util.UUID;

@Value.Immutable
public interface BeginneHaushaltsbuchfuehrung {
    UUID id();
}
