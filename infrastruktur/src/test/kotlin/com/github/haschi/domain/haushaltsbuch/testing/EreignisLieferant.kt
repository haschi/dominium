package com.github.haschi.domain.haushaltsbuch.testing

import com.github.haschi.domain.haushaltsbuch.modell.core.values.Aggregatkennung
import com.github.haschi.domain.haushaltsbuch.projektion.VergangeneEreignisse
import org.axonframework.eventsourcing.DomainEventMessage
import org.axonframework.eventsourcing.eventstore.EventStore

class EreignisLieferant(private val eventStore: EventStore) : VergangeneEreignisse
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