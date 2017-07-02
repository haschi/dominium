package com.github.haschi.haushaltsbuch.abfrage;

import com.github.haschi.haushaltsbuch.api.ImmutableHaushaltsbuchAngelegt;
import org.axonframework.eventsourcing.GenericDomainEventMessage;

import java.util.UUID;

public class AggregateProxy<T>
{
    private UUID identifier;
    private String type;
    private long sequenznummer;

    public AggregateProxy(final Class<T> clazz, final UUID uuid)
    {
        type = clazz.getSimpleName();
        identifier = uuid;
    }

    public GenericDomainEventMessage<Object> apply(
            ImmutableHaushaltsbuchAngelegt haushaltsbuchAngelegt)
    {
        return new GenericDomainEventMessage<>(
                getType(),
                getIdentifier().toString(),
                nächsteSequenznummer(),
                haushaltsbuchAngelegt);
    }

    public UUID getIdentifier()
    {
        return identifier;
    }

    public String getType()
    {
        return type;
    }

    private long nächsteSequenznummer()
    {
        return sequenznummer++;
    }
}
