package com.github.haschi.dominium.haushaltsbuch.core.application

import com.github.haschi.dominium.haushaltsbuch.core.domain.Historie
import com.github.haschi.dominium.haushaltsbuch.core.model.Zeit
import org.axonframework.commandhandling.CommandBus
import org.axonframework.config.Configuration
import org.axonframework.eventsourcing.eventstore.EventStore
import org.axonframework.queryhandling.QueryBus
import org.axonframework.serialization.Serializer

interface Infrastrukturfabrik
{
    fun serializer(konfiguration: Configuration): Serializer
    fun eventStore(konfiguration: Configuration): EventStore
    fun commandBus(konfiguration: Configuration): CommandBus
    fun queryBus(konfiguration: Configuration): QueryBus
    fun historie(konfiguration: Configuration): Historie
    fun zeit(): Zeit
}