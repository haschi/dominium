package com.github.haschi.haushaltsbuch.infrastruktur

import org.axonframework.config.Configuration
import org.axonframework.eventsourcing.eventstore.EmbeddedEventStore
import org.axonframework.eventsourcing.eventstore.EventStore
import org.axonframework.eventsourcing.eventstore.inmemory.InMemoryEventStorageEngine

class AxonInfrastrukturFactory
{
    fun eventstore(konfiguration: Configuration): EventStore =
            eventStore

    companion object
    {
        private val eventStore = EmbeddedEventStore(InMemoryEventStorageEngine())
    }
}