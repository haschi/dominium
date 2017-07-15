package com.github.haschi.haushaltsbuch.api.kommando;

import com.github.haschi.haushaltsbuch.api.Aggregatkennung;
import com.github.haschi.modeling.de.Anweisung;
import org.axonframework.commandhandling.annotation.TargetAggregateIdentifier;

import javax.money.MonetaryAmount;

@Anweisung("Buche Ein&shy;nahme")
public interface BucheEinnahme
{

    @TargetAggregateIdentifier
    Aggregatkennung haushaltsbuchId();

    String sollkonto();

    String habenkonto();

    MonetaryAmount geldbetrag();
}
