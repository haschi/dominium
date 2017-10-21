package org.github.haschi.infrastruktur;

import org.axonframework.config.Configuration;
import org.axonframework.eventsourcing.eventstore.EmbeddedEventStore;
import org.axonframework.eventsourcing.eventstore.EventStorageEngine;
import org.axonframework.eventsourcing.eventstore.EventStore;
import org.axonframework.eventsourcing.eventstore.inmemory.InMemoryEventStorageEngine;

@SuppressWarnings("WeakerAccess")
public final class EventStoreLieferant
{

    private final EventStorageEngine engine = new InMemoryEventStorageEngine();
    private final EmbeddedEventStore eventStore = new EmbeddedEventStore(engine);

    public EventStore eventBus(final Configuration konfiguration)
    {
        return eventStore;
    }
}
