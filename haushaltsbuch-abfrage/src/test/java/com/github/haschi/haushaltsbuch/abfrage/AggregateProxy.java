package com.github.haschi.haushaltsbuch.abfrage;

import com.github.haschi.haushaltsbuch.api.Aggregatkennung;
import com.github.haschi.haushaltsbuch.api.ImmutableHaushaltsbuchAngelegt;
import org.axonframework.eventsourcing.GenericDomainEventMessage;

public class AggregateProxy<T>
{
    private final Aggregatkennung identifier;
    private final String type;
    private long sequenznummer;

    public AggregateProxy(final Class<T> clazz, final Aggregatkennung uuid)
    {
        type = clazz.getSimpleName();
        identifier = uuid;
    }

    public GenericDomainEventMessage<Object> apply(
            final ImmutableHaushaltsbuchAngelegt haushaltsbuchAngelegt)
    {
        return new GenericDomainEventMessage<>(
                getType(),
                getIdentifier().wert().toString(),
                nächsteSequenznummer(),
                haushaltsbuchAngelegt);
    }

    public Aggregatkennung getIdentifier()
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
