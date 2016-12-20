package com.github.haschi.haushaltsbuch.api.kommando;

import com.github.haschi.modeling.de.Anweisung;
import org.axonframework.commandhandling.annotation.TargetAggregateIdentifier;

import javax.money.MonetaryAmount;
import java.util.UUID;

@Anweisung("Buche Anfangs&shy;bestand")
public interface BucheAnfangsbestand
{
    @TargetAggregateIdentifier
    UUID haushaltsbuchId();

    String kontobezeichnung();

    MonetaryAmount geldbetrag();
}
