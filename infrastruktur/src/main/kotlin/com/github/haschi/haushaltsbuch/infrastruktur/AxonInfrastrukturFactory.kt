package com.github.haschi.haushaltsbuch.infrastruktur

import com.github.haschi.dominium.haushaltsbuch.core.application.Infrastrukturfabrik
import org.axonframework.config.Configuration
import org.axonframework.eventsourcing.eventstore.EmbeddedEventStore
import org.axonframework.eventsourcing.eventstore.EventStore
import org.axonframework.eventsourcing.eventstore.inmemory.InMemoryEventStorageEngine

class AxonInfrastrukturFactory
    : Infrastrukturfabrik
{
    override fun eventstore(konfiguration: Configuration): EventStore =
            eventStore

    companion object
    {
        private val eventStore = EmbeddedEventStore(InMemoryEventStorageEngine())
    }
}