package com.github.haschi.haushaltsbuch.infrastruktur

import com.github.haschi.domain.haushaltsbuch.modell.core.values.Aggregatkennung
import com.github.haschi.domain.haushaltsbuch.projektion.Historie
import org.axonframework.eventsourcing.DomainEventMessage
import org.axonframework.eventsourcing.eventstore.EventStore

class EreignisLieferant(private val eventStore: EventStore) : Historie
{
    override fun bezüglich(aggregat: Aggregatkennung): Sequence<DomainEventMessage<*>>
    {
        return Sequence {
            eventStore
                .readEvents(aggregat.toString())
                .asStream()
                .iterator()
        }
    }
}