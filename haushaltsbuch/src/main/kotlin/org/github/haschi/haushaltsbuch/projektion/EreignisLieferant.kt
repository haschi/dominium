package org.github.haschi.haushaltsbuch.projektion

import org.axonframework.eventsourcing.DomainEventMessage
import org.axonframework.eventsourcing.eventstore.EventStore
import org.github.haschi.haushaltsbuch.modell.core.values.Aggregatkennung
import java.util.stream.Stream

class EreignisLieferant(private val eventStore: EventStore) : IEreignisLieferant
{

    override fun ereignisseVon(inventur: Aggregatkennung): Stream<out DomainEventMessage<*>>
    {
        return eventStore
                .readEvents(inventur.toString())
                .asStream()
    }
}