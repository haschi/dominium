package org.github.haschi.haushaltsbuch.core.events

import org.axonframework.commandhandling.TargetAggregateIdentifier
import org.github.haschi.haushaltsbuch.infrastruktur.modellierung.de.Aggregatkennung

data class BeendeInventur(
        @TargetAggregateIdentifier val von: Aggregatkennung)
