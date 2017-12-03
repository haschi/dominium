package com.github.haschi.domain.haushaltsbuch.modell.core.events

import com.github.haschi.domain.haushaltsbuch.modell.core.values.Aggregatkennung
import org.axonframework.commandhandling.TargetAggregateIdentifier

data class BeendeInventur(@TargetAggregateIdentifier val von: Aggregatkennung)
