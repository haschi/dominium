package com.github.haschi.haushaltsbuch.infrastruktur

import com.github.haschi.dominium.haushaltsbuch.core.application.Infrastrukturfabrik
import com.github.haschi.dominium.haushaltsbuch.core.domain.Historie
import org.axonframework.commandhandling.CommandBus
import org.axonframework.commandhandling.CommandCallback
import org.axonframework.commandhandling.SimpleCommandBus
import org.axonframework.config.Configuration
import org.axonframework.eventsourcing.eventstore.EmbeddedEventStore
import org.axonframework.eventsourcing.eventstore.EventStore
import org.axonframework.eventsourcing.eventstore.inmemory.InMemoryEventStorageEngine
import org.axonframework.messaging.Message
import org.axonframework.messaging.interceptors.CorrelationDataInterceptor
import org.axonframework.monitoring.MessageMonitor

class AxonInfrastrukturFactory : Infrastrukturfabrik
{
    override fun commandBus(konfiguration: Configuration): CommandBus
    {
        val commandBus = SimpleCommandBus()
        commandBus.registerHandlerInterceptor(CorrelationDataInterceptor(konfiguration.correlationDataProviders()));
        commandBus.registerHandlerInterceptor(CommandLoggingInterceptor)
        return commandBus
    }

    override fun logger(): CommandCallback<Any, Any> =
        LoggingCallback.INSTANCE

    override fun historie(konfiguration: Configuration): Historie =
         EreignisLieferant(konfiguration.eventStore())

    override fun eventStore(konfiguration: Configuration): EventStore =
            EmbeddedEventStore(InMemoryEventStorageEngine())

    override fun loggingMonitor(konfiguration: Configuration,
            name: String): MessageMonitor<Message<*>> =
            LoggingMonitor(name)
}