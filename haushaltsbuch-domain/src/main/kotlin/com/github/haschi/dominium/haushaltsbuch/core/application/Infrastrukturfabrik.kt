package com.github.haschi.dominium.haushaltsbuch.core.application

import org.axonframework.config.Configuration
import org.axonframework.eventsourcing.eventstore.EventStore

interface Infrastrukturfabrik
{
    fun eventstore(konfiguration: Configuration): EventStore
}