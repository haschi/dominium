package com.github.haschi.haushaltsbuch.api.kommando;

import com.github.haschi.modeling.de.Anweisung;
import org.axonframework.commandhandling.annotation.TargetAggregateIdentifier;

import javax.money.MonetaryAmount;
import java.util.UUID;

@Anweisung("Buche Tilgung")
public interface BucheTilgung
{

    @TargetAggregateIdentifier
    UUID haushaltsbuchId();

    String sollkonto();

    String habenkonto();

    MonetaryAmount waehrungsbetrag();
}
