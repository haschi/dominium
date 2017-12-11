package com.github.haschi.dominium.haushaltsbuch.core.application

import com.github.haschi.dominium.haushaltsbuch.core.domain.Historie
import org.axonframework.commandhandling.CommandCallback
import org.axonframework.config.Configuration
import org.axonframework.eventsourcing.eventstore.EventStore

interface Infrastrukturfabrik
{
    fun eventstore(konfiguration: Configuration): EventStore
    fun historie(konfiguration: Configuration): Historie
    fun logger(): CommandCallback<Any, Any>
}