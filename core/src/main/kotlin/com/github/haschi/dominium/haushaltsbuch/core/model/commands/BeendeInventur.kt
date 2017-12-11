package com.github.haschi.dominium.haushaltsbuch.core.model.commands

import com.github.haschi.dominium.haushaltsbuch.core.model.values.Aggregatkennung
import org.axonframework.commandhandling.TargetAggregateIdentifier

data class BeendeInventur(@TargetAggregateIdentifier val von: Aggregatkennung)
