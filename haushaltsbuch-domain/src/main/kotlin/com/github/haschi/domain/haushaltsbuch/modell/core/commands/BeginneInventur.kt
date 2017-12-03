package com.github.haschi.domain.haushaltsbuch.modell.core.commands

import com.github.haschi.domain.haushaltsbuch.modell.core.values.Aggregatkennung
import org.axonframework.commandhandling.TargetAggregateIdentifier

data class BeginneInventur(@TargetAggregateIdentifier val id: Aggregatkennung)
