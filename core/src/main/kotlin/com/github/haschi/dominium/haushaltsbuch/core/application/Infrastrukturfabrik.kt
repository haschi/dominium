package com.github.haschi.dominium.haushaltsbuch.core.application

import com.github.haschi.dominium.haushaltsbuch.core.domain.Historie
import org.axonframework.commandhandling.CommandCallback
import org.axonframework.config.Configuration
import org.axonframework.eventsourcing.eventstore.EventStore
import org.axonframework.messaging.Message
import org.axonframework.monitoring.MessageMonitor

interface Infrastrukturfabrik
{
    fun eventstore(konfiguration: Configuration): EventStore
    fun historie(konfiguration: Configuration): Historie
    fun logger(): CommandCallback<Any, Any>
    fun loggingMonitor(konfiguration: Configuration, name: String): MessageMonitor<Message<*>>
}