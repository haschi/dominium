package com.github.haschi.domain.haushaltsbuch.projektion

import com.github.haschi.domain.haushaltsbuch.modell.core.values.Aggregatkennung
import org.axonframework.eventsourcing.DomainEventMessage

interface VergangeneEreignisse
{
    fun bezüglich(aggregat: Aggregatkennung): Sequence<DomainEventMessage<*>>
}