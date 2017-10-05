package org.github.haschi.haushaltsbuch.projektion;

import javaslang.collection.Stream;
import org.axonframework.commandhandling.CommandHandler;
import org.axonframework.config.Configuration;
import org.axonframework.eventsourcing.DomainEventMessage;
import org.axonframework.eventsourcing.eventstore.DomainEventStream;
import org.axonframework.eventsourcing.eventstore.EventStore;
import org.github.haschi.haushaltsbuch.api.Inventar;
import org.github.haschi.haushaltsbuch.api.InventarErfasst;
import org.github.haschi.haushaltsbuch.api.LeseInventar;
import org.github.haschi.haushaltsbuch.api.Schulden;
import org.github.haschi.haushaltsbuch.api.Vermögenswerte;

import java.util.function.Predicate;
import java.util.stream.Collectors;

import static javaslang.API.*;

public class InventarProjektion
{

    private final Configuration konfiguration;

    public InventarProjektion(final Configuration konfiguration)
    {
        this.konfiguration = konfiguration;
    }

    @CommandHandler
    public Inventar leseInventar(final LeseInventar abfrage)
    {
        final EventStore eventStore = konfiguration.eventStore();
        final DomainEventStream eventStream = eventStore.readEvents(abfrage.ausInventur().toString());

        final Inventar.Builder builder = Inventar.builder()
                .anlagevermögen(Vermögenswerte.leer())
                .umlaufvermögen(Vermögenswerte.leer())
                .schulden(Schulden.leer());

        Stream.ofAll(eventStream.asStream().collect(Collectors.toList()))
                .foldLeft(builder, InventarProjektion::ie)
                .build();

        return builder.build();
    }

    private static Inventar.Builder ie(final Inventar.Builder builder, final DomainEventMessage message)
    {
        return Match(message.getPayload()).of(
                Case(ereignis(InventarErfasst.class), h -> builder.from(h.inventar())),
                Case($(), h -> builder));
    }

    private static <T> Predicate<T> ereignis(final Class<? super T> type)
    {
        return (T obj) -> type.isAssignableFrom(obj.getClass());
    }
}
