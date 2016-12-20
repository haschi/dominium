package com.github.haschi.haushaltsbuch.abfragen;

import com.github.haschi.haushaltsbuch.api.Kontoart;
import com.github.haschi.haushaltsbuch.api.ereignis.HauptbuchWurdeAngelegt;
import com.github.haschi.haushaltsbuch.api.ereignis.KontoWurdeAngelegt;
import com.github.haschi.haushaltsbuch.domaene.aggregat.Haushaltsbuch;
import javaslang.API;
import javaslang.Predicates;
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

        return DomainEventStreamFactory.create(this.eventStore, Haushaltsbuch.class, haushaltsbuchId)
                .foldLeft(ImmutableHauptbuchAnsicht.builder(), (b, e) -> API.Match(e.getPayload()).of(
                        API.Case(ereignis(HauptbuchWurdeAngelegt.class), h -> b.haushaltsbuchId(h.haushaltsbuchId())),
                        API.Case(
                                ereignis(KontoWurdeAngelegt.class),
                                k -> API.Match(k.kontoart())
                                        .of(API.Case(
                                                Predicates.is(Kontoart.Aktiv),
                                                x -> b.addAktivkonten(k.kontoname())),
                                                API.Case(
                                                        Predicates.is(Kontoart.Passiv),
                                                        x -> b.addPassivkonten(k.kontoname())),
                                                API.Case(
                                                        Predicates.is(Kontoart.Ertrag),
                                                        x -> b.addErtragskonten(k.kontoname())),
                                                API.Case(Predicates.is(Kontoart.Aufwand),
                                                        x -> b.addAufwandskonten(k.kontoname())),
                                                API.Case($(), () ->
                                                {
                                                    throw new NoClassDefFoundError();
                                                }))),
                        API.Case($(), h -> b)))
                .build();
    }

    public static <T> Predicate<T> ereignis(final Class<? super T> type)
    {
        return (T obj) -> type.isAssignableFrom(obj.getClass());
    }
}
