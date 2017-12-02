package org.github.haschi.domain.haushaltsbuch.projektion

import org.axonframework.eventsourcing.DomainEventMessage
import org.github.haschi.domain.haushaltsbuch.modell.core.values.Aggregatkennung
import java.util.stream.Stream

interface VergangeneEreignisse
{
    fun bezüglich(aggregat: Aggregatkennung): Sequence<out DomainEventMessage<*>>
}