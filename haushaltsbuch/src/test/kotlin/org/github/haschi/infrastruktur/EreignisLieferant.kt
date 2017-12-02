package org.github.haschi.infrastruktur

import org.axonframework.eventsourcing.DomainEventMessage
import org.axonframework.eventsourcing.eventstore.EventStore
import org.github.haschi.haushaltsbuch.modell.core.values.Aggregatkennung
import org.github.haschi.haushaltsbuch.projektion.VergangeneEreignisse

class EreignisLieferant(private val eventStore: EventStore) : VergangeneEreignisse
{

    override fun bezüglich(aggregat: Aggregatkennung): Sequence<out DomainEventMessage<*>>
    {
        return Sequence {
            eventStore
                .readEvents(aggregat.toString())
                .asStream()
                .iterator()
        }
    }
}