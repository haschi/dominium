package com.github.haschi.dominium.haushaltsbuch.core.model.commands

import com.github.haschi.dominium.haushaltsbuch.core.model.values.Aggregatkennung
import com.github.haschi.dominium.haushaltsbuch.core.model.values.Inventar
import org.axonframework.commandhandling.TargetAggregateIdentifier

data class ErfasseInventar(
        @TargetAggregateIdentifier val id: Aggregatkennung,
        val inventar: Inventar)
