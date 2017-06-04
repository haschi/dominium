package com.github.haschi.haushaltsbuch.abfragen;

import com.github.haschi.haushaltsbuch.api.HauptbuchWurdeAngelegt;
import com.github.haschi.haushaltsbuch.api.KontoWurdeAngelegt;
import com.github.haschi.haushaltsbuch.api.Kontoart;
import com.github.haschi.haushaltsbuch.domaene.aggregat.Haushaltsbuch;
import org.axonframework.domain.DomainEventMessage;
import org.axonframework.eventstore.EventStore;

import javax.inject.Inject;
import java.util.UUID;
import java.util.function.Predicate;

import static javaslang.API.*;
import static javaslang.Predicates.*;

public class HauptbuchAbfrage
{
    @Inject
    private EventStore eventStore;

    public static <T> Predicate<T> ereignis(final Class<? super T> type)
    {
        return (T obj) -> type.isAssignableFrom(obj.getClass());
    }

    public HauptbuchAnsicht abfragen(final UUID haushaltsbuchId)
    {
        return DomainEventStreamFactory
                .create(this.eventStore, Haushaltsbuch.class, haushaltsbuchId)
                .foldLeft(ImmutableHauptbuchAnsicht.builder(), this::ereignisseFalten)
                .build();
    }

    private ImmutableHauptbuchAnsicht.Builder ereignisseFalten(
            final ImmutableHauptbuchAnsicht.Builder builder,
            final DomainEventMessage message)
    {
        return Match(message.getPayload()).of(
                Case(
                        ereignis(HauptbuchWurdeAngelegt.class),
                        h -> builder.haushaltsbuchId(h.haushaltsbuchId())),
                Case(ereignis(KontoWurdeAngelegt.class), k -> kontoHinzufügen(builder, k)),
                Case($(), h -> builder));
    }

    private ImmutableHauptbuchAnsicht.Builder kontoHinzufügen(
            final ImmutableHauptbuchAnsicht.Builder builder,
            final KontoWurdeAngelegt ereignis)
    {
        return Match(ereignis.kontoart()).of(
                Case(is(Kontoart.Aktiv), x -> builder.addAktivkonten(ereignis.kontobezeichnung())),
                Case(is(Kontoart.Passiv), x -> builder.addPassivkonten(ereignis.kontobezeichnung())),
                Case(is(Kontoart.Ertrag), x -> builder.addErtragskonten(ereignis.kontobezeichnung())),
                Case(is(Kontoart.Aufwand), x -> builder.addAufwandskonten(ereignis.kontobezeichnung())),
                Case($(), () ->
                {
                    throw new NoClassDefFoundError();
                }));
    }
}
