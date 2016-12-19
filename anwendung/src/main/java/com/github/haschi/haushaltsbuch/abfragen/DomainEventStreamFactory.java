package com.github.haschi.haushaltsbuch.abfragen;

import javaslang.collection.Stream;
import org.axonframework.domain.DomainEventMessage;
import org.axonframework.domain.DomainEventStream;

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
}
