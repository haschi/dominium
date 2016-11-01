package com.github.haschi.haushaltsbuch.abfragen;

import com.github.haschi.haushaltsbuch.api.ereignis.HauptbuchWurdeAngelegt;
import com.github.haschi.haushaltsbuch.api.ereignis.KontoWurdeAngelegt;
import com.github.haschi.haushaltsbuch.domaene.aggregat.Haushaltsbuch;
import org.axonframework.domain.DomainEventMessage;
import org.axonframework.domain.DomainEventStream;
import org.axonframework.domain.Message;
import org.axonframework.eventstore.EventStore;

import javax.inject.Inject;
import java.util.Optional;
import java.util.UUID;
import java.util.function.Consumer;

public class HauptbuchAbfrage
{

    @Inject
    private EventStore eventStore;

    public HauptbuchAnsicht abfragen(final UUID haushaltsbuchId)
    {

        final ImmutableHauptbuchAnsicht.Builder builder = ImmutableHauptbuchAnsicht.builder();

        final DomainEventStream domainEventStream = this.eventStore.readEvents(Haushaltsbuch.class.getSimpleName(),
                haushaltsbuchId);

        while (domainEventStream.hasNext())
        {
            final DomainEventMessage message = domainEventStream.peek();

            switchType(message,
                    falls(HauptbuchWurdeAngelegt.class, h -> builder.haushaltsbuchId(h.haushaltsbuchId())),
                    falls(KontoWurdeAngelegt.class, k ->
                    {
                        switch (k.kontoart())
                        {
                            case Aktiv:
                                builder.addAktivkonten(k.kontoname());
                                break;
                            case Passiv:
                                builder.addPassivkonten(k.kontoname());
                                break;
                            case Ertrag:
                                builder.addErtragskonten(k.kontoname());
                                break;
                            case Aufwand:
                                builder.addAufwandskonten(k.kontoname());
                                break;
                            default:
                                throw new IllegalStateException();
                        }
                    }));

            domainEventStream.next();
        }

        return builder.build();
    }

    public void switchType(final DomainEventMessage message, final Consumer... consumers)
    {
        for (final Consumer consumer : consumers)
        {
            consumer.accept(message);
        }
    }

    public static <T> Consumer<DomainEventMessage> falls(
            final Class<T> clazz, final Consumer<T> consumer)
    {
        return (DomainEventMessage obj) -> Optional.of(obj)
                .filter(m -> clazz.isAssignableFrom(m.getPayloadType()))
                .map(Message::getPayload)
                .map(clazz::cast)
                .ifPresent(consumer);
    }
}
