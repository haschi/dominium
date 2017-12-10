package com.github.haschi.haushaltsbuch.infrastruktur

import com.github.haschi.domain.haushaltsbuch.projektion.Historie
import com.github.haschi.dominium.haushaltsbuch.core.application.Infrastrukturfabrik
import org.axonframework.commandhandling.CommandCallback
import org.axonframework.config.Configuration
import org.axonframework.eventsourcing.eventstore.EmbeddedEventStore
import org.axonframework.eventsourcing.eventstore.EventStore
import org.axonframework.eventsourcing.eventstore.inmemory.InMemoryEventStorageEngine

class AxonInfrastrukturFactory : Infrastrukturfabrik
{
    override fun logger(): CommandCallback<Any, Any> =
        LoggingCallback.INSTANCE

    override fun historie(konfiguration: Configuration): Historie =
         EreignisLieferant(konfiguration.eventStore())

    override fun eventstore(konfiguration: Configuration): EventStore =
            EmbeddedEventStore(InMemoryEventStorageEngine())
}