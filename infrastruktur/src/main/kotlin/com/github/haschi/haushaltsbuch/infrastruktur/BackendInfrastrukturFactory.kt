package com.github.haschi.haushaltsbuch.infrastruktur

import com.fasterxml.jackson.databind.ObjectMapper
import com.github.haschi.dominium.haushaltsbuch.core.application.Infrastrukturfabrik
import com.github.haschi.dominium.haushaltsbuch.core.domain.Historie
import com.github.haschi.dominium.haushaltsbuch.core.model.Zeit
import org.axonframework.commandhandling.CommandBus
import org.axonframework.commandhandling.CommandMessage
import org.axonframework.commandhandling.SimpleCommandBus
import org.axonframework.config.Configuration
import org.axonframework.eventsourcing.eventstore.EmbeddedEventStore
import org.axonframework.eventsourcing.eventstore.EventStore
import org.axonframework.messaging.interceptors.CorrelationDataInterceptor
import org.axonframework.mongo.DefaultMongoTemplate
import org.axonframework.mongo.eventsourcing.eventstore.MongoEventStorageEngine
import org.axonframework.mongo.eventsourcing.eventstore.documentpercommit.DocumentPerCommitStorageStrategy
import org.axonframework.queryhandling.QueryBus
import org.axonframework.queryhandling.SimpleQueryBus
import org.axonframework.serialization.Serializer
import org.axonframework.serialization.json.JacksonSerializer

class BackendInfrastrukturFactory(
        val mapper: ObjectMapper,
        val template: DefaultMongoTemplate) : Infrastrukturfabrik
{
    override fun zeit(): Zeit
    {
        return SystemZeit
    }

    override fun queryBus(konfiguration: Configuration): QueryBus
    {
        val queryBus = SimpleQueryBus()
        queryBus.registerHandlerInterceptor(LoggingInterceptor("QUERY  "))

        return queryBus
    }

    override fun commandBus(konfiguration: Configuration): CommandBus
    {
        val correlationDataInterceptor = CorrelationDataInterceptor<CommandMessage<*>>(
                konfiguration.correlationDataProviders())

        val commandBus = SimpleCommandBus()

        commandBus.registerHandlerInterceptor(correlationDataInterceptor)
        commandBus.registerHandlerInterceptor(LoggingInterceptor("COMMAND"))

        return commandBus
    }

    override fun historie(konfiguration: Configuration): Historie =
         EreignisLieferant(konfiguration.eventStore())

    override fun serializer(konfiguration: Configuration): Serializer
    {
        return JacksonSerializer(mapper)
    }

    override fun eventStore(konfiguration: Configuration): EventStore
    {
        val engine = MongoEventStorageEngine(
                serializer(konfiguration),
                null,
                template,
                DocumentPerCommitStorageStrategy())

        val eventStore = EmbeddedEventStore(engine)
        eventStore.registerDispatchInterceptor(EventLoggingInterceptor())

        return eventStore
    }
}