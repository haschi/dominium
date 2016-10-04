package com.github.haschi.haushaltsbuch.api.kommando;

import org.axonframework.commandhandling.annotation.TargetAggregateIdentifier;
import org.immutables.value.Value;

import javax.money.MonetaryAmount;
import java.util.UUID;

@Value.Immutable
public interface FügeBuchungssatzHinzu
{

    @TargetAggregateIdentifier
    UUID identitaetsmerkmal();

    String sollkonto();

    String habenkonto();

    MonetaryAmount betrag();
}
