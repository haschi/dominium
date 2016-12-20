package com.github.haschi.haushaltsbuch.abfragen;

import javaslang.collection.Stream;
import org.axonframework.domain.DomainEventMessage;
import org.axonframework.domain.DomainEventStream;
import org.axonframework.eventstore.EventStore;

public class DomainEventStreamFactory
{
    public static Stream<DomainEventMessage> create(final DomainEventStream s)
    {
        if (s.hasNext())
        {
            final DomainEventMessage aktuellesElement = s.peek();
            s.next();

            return Stream.cons(aktuellesElement, () -> create(s));
        }
        else
        {
            return Stream.Empty.instance();
        }
    }

    public static <T> Stream<DomainEventMessage> create(
            final EventStore eventStore, final Class<T> aggregateType, final Object haushaltsbuchId)
    {
        return create(eventStore.readEvents(aggregateType.getSimpleName(), haushaltsbuchId));
    }
}
