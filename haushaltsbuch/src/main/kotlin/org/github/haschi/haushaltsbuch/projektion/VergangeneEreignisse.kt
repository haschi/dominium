package org.github.haschi.haushaltsbuch.projektion

import org.axonframework.eventsourcing.DomainEventMessage
import org.github.haschi.haushaltsbuch.modell.core.values.Aggregatkennung
import java.util.stream.Stream

interface VergangeneEreignisse
{
    fun ereignisseVon(inventur: Aggregatkennung): Stream<out DomainEventMessage<*>>
}