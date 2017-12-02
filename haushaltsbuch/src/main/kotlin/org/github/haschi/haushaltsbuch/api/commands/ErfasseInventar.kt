package org.github.haschi.haushaltsbuch.api.commands

import org.axonframework.commandhandling.TargetAggregateIdentifier
import org.github.haschi.haushaltsbuch.core.Inventar
import org.github.haschi.haushaltsbuch.infrastruktur.modellierung.de.Aggregatkennung

data class ErfasseInventar(
        @TargetAggregateIdentifier val für: Aggregatkennung,
        val inventar: Inventar)
