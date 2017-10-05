package org.github.haschi.haushaltsbuch.api;

import org.axonframework.commandhandling.TargetAggregateIdentifier;
import org.github.haschi.haushaltsbuch.infrastruktur.modellierung.de.Aggregatkennung;
import org.github.haschi.haushaltsbuch.infrastruktur.modellierung.de.Anweisung;

@Anweisung
public interface _ErfasseInventar
{
    @TargetAggregateIdentifier
    Aggregatkennung für();

    Inventar inventar();
}
