package org.github.haschi.haushaltsbuch.api

import org.axonframework.commandhandling.TargetAggregateIdentifier
import org.github.haschi.haushaltsbuch.infrastruktur.modellierung.de.Aggregatkennung

data class ErfasseInventar(
        @TargetAggregateIdentifier val für: Aggregatkennung,
        val inventar: Inventar)
