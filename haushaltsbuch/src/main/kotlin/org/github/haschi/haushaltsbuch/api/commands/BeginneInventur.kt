package org.github.haschi.haushaltsbuch.api.commands

import org.axonframework.commandhandling.TargetAggregateIdentifier
import org.github.haschi.haushaltsbuch.infrastruktur.modellierung.de.Aggregatkennung

data class BeginneInventur(@TargetAggregateIdentifier val id: Aggregatkennung)
