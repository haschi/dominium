package com.github.haschi.domain.haushaltsbuch.testing

import com.github.haschi.dominium.haushaltsbuch.core.application.Infrastrukturfabrik
import com.github.haschi.dominium.haushaltsbuch.core.domain.Historie
import com.github.haschi.dominium.haushaltsbuch.core.model.Zeit
import com.github.haschi.haushaltsbuch.infrastruktur.EreignisLieferant
import com.github.haschi.haushaltsbuch.infrastruktur.EventLoggingInterceptor
import com.github.haschi.haushaltsbuch.infrastruktur.LoggingInterceptor
import org.axonframework.commandhandling.CommandBus
import org.axonframework.commandhandling.CommandMessage
import org.axonframework.commandhandling.SimpleCommandBus
import org.axonframework.config.Configuration
import org.axonframework.eventsourcing.eventstore.EmbeddedEventStore
import org.axonframework.eventsourcing.eventstore.EventStore
import org.axonframework.eventsourcing.eventstore.inmemory.InMemoryEventStorageEngine
import org.axonframework.messaging.interceptors.CorrelationDataInterceptor
import org.axonframework.queryhandling.QueryBus
import org.axonframework.queryhandling.SimpleQueryBus
import org.axonframework.serialization.Serializer

class TestInfrastrukturFactory : Infrastrukturfabrik
{
    override fun zeit(): Zeit
    {
        return Testzeit
    }

    override fun queryBus(konfiguration: Configuration): QueryBus
    {
        val queryBus = SimpleQueryBus()
        queryBus.registerHandlerInterceptor(LoggingInterceptor(
                "QUERY  "))

        return queryBus
    }

    override fun commandBus(konfiguration: Configuration): CommandBus
    {
        val correlationDataInterceptor = CorrelationDataInterceptor<CommandMessage<*>>(
                konfiguration.correlationDataProviders())

        val commandBus = SimpleCommandBus()

        commandBus.registerHandlerInterceptor(correlationDataInterceptor)
        commandBus.registerHandlerInterceptor(LoggingInterceptor(
                "COMMAND"))

        return commandBus
    }

    override fun historie(konfiguration: Configuration): Historie =
            EreignisLieferant(konfiguration.eventStore())

    override fun serializer(konfiguration: Configuration): Serializer
    {
        return konfiguration.eventSerializer()
    }

    override fun eventStore(konfiguration: Configuration): EventStore
    {
        val eventStore = EmbeddedEventStore(InMemoryEventStorageEngine())
        eventStore.registerDispatchInterceptor(EventLoggingInterceptor())

        return eventStore
    }
}