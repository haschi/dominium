package com.github.haschi.haushaltsbuch.api.refaktorisiert;

import com.github.haschi.haushaltsbuch.api.Aggregatkennung;
import com.github.haschi.modeling.de.Anweisung;
import org.axonframework.commandhandling.TargetAggregateIdentifier;

@Anweisung
public interface ErstelleEröffnungsbilanz
{
    @TargetAggregateIdentifier
    Aggregatkennung id();

    Inventar inventar();
}
