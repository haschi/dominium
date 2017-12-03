package com.github.haschi.domain.haushaltsbuch.testing

import org.axonframework.config.Configuration
import org.axonframework.eventsourcing.eventstore.EmbeddedEventStore
import org.axonframework.eventsourcing.eventstore.EventStore
import org.axonframework.eventsourcing.eventstore.inmemory.InMemoryEventStorageEngine

class EventStoreLieferant
{

    private val engine = InMemoryEventStorageEngine()
    private val eventStore = EmbeddedEventStore(engine)

    fun eventBus(konfiguration: Configuration): EventStore
    {
        return eventStore
    }
}
