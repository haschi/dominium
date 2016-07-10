package com.github.haschi.haushaltsbuch.api.kommando;

import com.github.haschi.coding.annotation.TargetAggregateIdentifier;
import org.immutables.value.Value;

import javax.money.MonetaryAmount;
import java.util.UUID;

@Value.Immutable
public interface BucheEinnahme {

    @TargetAggregateIdentifier
    UUID haushaltsbuchId();

    String sollkonto();
    String habenkonto();
    MonetaryAmount waehrungsbetrag();
}
