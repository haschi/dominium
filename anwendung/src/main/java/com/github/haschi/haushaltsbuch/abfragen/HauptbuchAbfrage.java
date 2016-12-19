package com.github.haschi.haushaltsbuch.abfragen;

import com.github.haschi.haushaltsbuch.api.Kontoart;
import com.github.haschi.haushaltsbuch.api.ereignis.HauptbuchWurdeAngelegt;
import com.github.haschi.haushaltsbuch.api.ereignis.KontoWurdeAngelegt;
import com.github.haschi.haushaltsbuch.domaene.aggregat.Haushaltsbuch;
import javaslang.API;
import javaslang.Predicates;
import org.axonframework.domain.DomainEventMessage;
import org.axonframework.domain.DomainEventStream;
import org.axonframework.eventstore.EventStore;

import javax.inject.Inject;
import java.util.UUID;
import java.util.function.Predicate;

import static javaslang.API.$;

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

            API.Match(message.getPayload()).of(
                    API.Case(ereignis(HauptbuchWurdeAngelegt.class), h -> builder.haushaltsbuchId(h.haushaltsbuchId())),
                    API.Case(ereignis(KontoWurdeAngelegt.class), k ->
                    {
                        return API.Match(k.kontoart())
                                .of(API.Case(Predicates.is(Kontoart.Aktiv), x -> builder.addAktivkonten(k.kontoname())),
                                        API.Case(Predicates.is(Kontoart.Passiv),
                                                x -> builder.addPassivkonten(k.kontoname())),
                                        API.Case(Predicates.is(Kontoart.Ertrag),
                                                x -> builder.addErtragskonten(k.kontoname())),
                                        API.Case(Predicates.is(Kontoart.Aufwand),
                                                x -> builder.addAufwandskonten(k.kontoname())),
                                        API.Case($(), () ->
                                        {
                                            throw new NoClassDefFoundError();
                                        }));
                    }),
                    API.Case($(), h -> builder));

            domainEventStream.next();
        }

        return builder.build();
    }

    public static <T> Predicate<T> ereignis(final Class<? super T> type)
    {
        return (T obj) ->
        {
            final boolean result = type.isAssignableFrom(obj.getClass());
            System.out.println(result);
            return result;
        };
    }

}
