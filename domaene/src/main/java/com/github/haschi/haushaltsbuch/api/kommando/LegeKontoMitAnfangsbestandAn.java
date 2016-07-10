package com.github.haschi.haushaltsbuch.api.kommando;

import com.github.haschi.haushaltsbuch.api.Kontoart;
import org.axonframework.commandhandling.annotation.TargetAggregateIdentifier;
import org.immutables.value.Value;

import javax.money.MonetaryAmount;
import java.util.UUID;

@Value.Immutable
public interface LegeKontoMitAnfangsbestandAn {

    @TargetAggregateIdentifier
    UUID haushaltsbuchId();

    String kontoname();

    Kontoart kontoart();

    MonetaryAmount betrag();
}
