package com.github.haschi.dominium.haushaltsbuch.core.application

import com.github.haschi.dominium.haushaltsbuch.core.domain.Historie
import org.axonframework.commandhandling.CommandBus
import org.axonframework.config.Configuration
import org.axonframework.eventsourcing.eventstore.EventStore
import org.axonframework.queryhandling.QueryBus

interface Infrastrukturfabrik
{
    fun eventStore(konfiguration: Configuration): EventStore
    fun commandBus(konfiguration: Configuration): CommandBus
    fun queryBus(konfiguration: Configuration): QueryBus
    fun historie(konfiguration: Configuration): Historie
}