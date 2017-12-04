package com.github.haschi.domain.haushaltsbuch.testing

import org.axonframework.config.Configurer
import org.axonframework.config.DefaultConfigurer
import org.axonframework.eventsourcing.eventstore.EmbeddedEventStore
import org.axonframework.eventsourcing.eventstore.inmemory.InMemoryEventStorageEngine

class TestConfigurer
{
    companion object
    {
        fun createDefault(): Configurer
        {
            return DefaultConfigurer.defaultConfiguration()
                    .configureEventStore {_ -> EmbeddedEventStore(InMemoryEventStorageEngine())}
        }
    }
}