package com.github.haschi.dominium.haushaltsbuch.core.model.commands

import org.axonframework.commandhandling.TargetAggregateIdentifier
import com.github.haschi.dominium.haushaltsbuch.core.model.values.Aggregatkennung
import com.github.haschi.dominium.haushaltsbuch.core.model.values.Inventar

data class BeginneHaushaltsbuchführung(
        @TargetAggregateIdentifier val id: Aggregatkennung,
        val inventar: Inventar)
