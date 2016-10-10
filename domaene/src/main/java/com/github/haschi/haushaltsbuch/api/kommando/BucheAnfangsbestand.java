package com.github.haschi.haushaltsbuch.api.kommando;

import com.github.haschi.modeling.de.Anweisung;
import org.axonframework.commandhandling.annotation.TargetAggregateIdentifier;

import javax.money.MonetaryAmount;
import java.util.UUID;

@Anweisung("Buche Anfangsbestand")
public interface BucheAnfangsbestand
{
    @TargetAggregateIdentifier
    UUID haushaltsbuchId();

    String kontoname();

    MonetaryAmount waehrungsbetrag();
}
