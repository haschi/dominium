package org.github.haschi.haushaltsbuch.modell.core.commands

import org.axonframework.commandhandling.TargetAggregateIdentifier
import org.github.haschi.haushaltsbuch.modell.core.values.Inventar
import org.github.haschi.haushaltsbuch.modell.core.values.Aggregatkennung

data class ErfasseInventar(
        @TargetAggregateIdentifier val für: Aggregatkennung,
        val inventar: Inventar)
