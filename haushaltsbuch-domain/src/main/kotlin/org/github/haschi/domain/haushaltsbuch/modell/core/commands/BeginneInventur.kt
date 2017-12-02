package org.github.haschi.domain.haushaltsbuch.modell.core.commands

import org.axonframework.commandhandling.TargetAggregateIdentifier
import org.github.haschi.domain.haushaltsbuch.modell.core.values.Aggregatkennung

data class BeginneInventur(@TargetAggregateIdentifier val id: Aggregatkennung)
