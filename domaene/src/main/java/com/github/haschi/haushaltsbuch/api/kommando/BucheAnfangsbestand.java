package com.github.haschi.haushaltsbuch.api.kommando;

import org.axonframework.commandhandling.annotation.TargetAggregateIdentifier;
import org.immutables.value.Value;

import javax.money.MonetaryAmount;
import java.util.UUID;

@Value.Immutable
public interface BucheAnfangsbestand {
    @TargetAggregateIdentifier
    UUID haushaltsbuchId();

    String kontoname();

    MonetaryAmount waehrungsbetrag();
}
