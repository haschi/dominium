package org.github.haschi.haushaltsbuch.modell.core.events

import org.axonframework.commandhandling.TargetAggregateIdentifier
import org.github.haschi.haushaltsbuch.modell.core.values.Aggregatkennung

data class BeendeInventur(
        @TargetAggregateIdentifier val von: Aggregatkennung)
