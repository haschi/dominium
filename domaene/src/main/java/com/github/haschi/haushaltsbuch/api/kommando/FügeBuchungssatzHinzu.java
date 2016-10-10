package com.github.haschi.haushaltsbuch.api.kommando;

import com.github.haschi.modeling.de.Anweisung;
import org.axonframework.commandhandling.annotation.TargetAggregateIdentifier;

import javax.money.MonetaryAmount;
import java.util.UUID;

@Anweisung("Füge Buchungssatz hinzu")
public interface FügeBuchungssatzHinzu
{

    @TargetAggregateIdentifier
    UUID identitaetsmerkmal();

    String sollkonto();

    String habenkonto();

    MonetaryAmount betrag();
}
