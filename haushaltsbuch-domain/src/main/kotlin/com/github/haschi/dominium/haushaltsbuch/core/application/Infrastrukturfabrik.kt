package com.github.haschi.dominium.haushaltsbuch.core.application

import com.github.haschi.domain.haushaltsbuch.projektion.Historie
import org.axonframework.config.Configuration
import org.axonframework.eventsourcing.eventstore.EventStore

interface Infrastrukturfabrik
{
    fun eventstore(konfiguration: Configuration): EventStore
    fun historie(konfiguration: Configuration): Historie
}