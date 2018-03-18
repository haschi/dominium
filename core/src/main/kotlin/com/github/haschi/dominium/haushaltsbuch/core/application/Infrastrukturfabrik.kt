package com.github.haschi.dominium.haushaltsbuch.core.application

import com.github.haschi.dominium.haushaltsbuch.core.domain.Historie
import org.axonframework.commandhandling.CommandBus
import org.axonframework.commandhandling.CommandCallback
import org.axonframework.commandhandling.CommandMessage
import org.axonframework.config.Configuration
import org.axonframework.eventsourcing.eventstore.EventStore
import org.axonframework.messaging.Message
import org.axonframework.messaging.MessageHandlerInterceptor
import org.axonframework.monitoring.MessageMonitor

interface Infrastrukturfabrik
{
    fun eventStore(konfiguration: Configuration): EventStore
    fun commandBus(konfiguration: Configuration): CommandBus
    fun historie(konfiguration: Configuration): Historie
    fun logger(): CommandCallback<Any, Any>
    fun loggingMonitor(konfiguration: Configuration, name: String): MessageMonitor<Message<*>>
}