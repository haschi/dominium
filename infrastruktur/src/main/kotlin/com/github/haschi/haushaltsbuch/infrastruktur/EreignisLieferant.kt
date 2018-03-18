package com.github.haschi.haushaltsbuch.infrastruktur

import com.github.haschi.dominium.haushaltsbuch.core.model.values.Aggregatkennung
import com.github.haschi.dominium.haushaltsbuch.core.domain.Historie
import org.axonframework.eventsourcing.DomainEventMessage
import org.axonframework.eventsourcing.eventstore.EventStore

class EreignisLieferant(private val eventStore: EventStore) : Historie
{
    override fun bezueglich(aggregat: Aggregatkennung): Sequence<DomainEventMessage<*>>
    {
        return Sequence {
            eventStore
                .readEvents(aggregat.toString())
                .asStream()
                .iterator()
        }
    }
}