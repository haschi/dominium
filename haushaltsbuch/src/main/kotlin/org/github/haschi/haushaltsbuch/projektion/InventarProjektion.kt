package org.github.haschi.haushaltsbuch.projektion

import org.axonframework.commandhandling.CommandHandler
import org.axonframework.config.Configuration
import org.axonframework.eventsourcing.DomainEventMessage
import org.axonframework.eventsourcing.eventstore.EventStore
import org.github.haschi.haushaltsbuch.modell.core.events.InventarErfasst
import org.github.haschi.haushaltsbuch.modell.core.queries.LeseInventar
import org.github.haschi.haushaltsbuch.modell.core.values.Aggregatkennung
import org.github.haschi.haushaltsbuch.modell.core.values.Inventar
import java.util.stream.Stream

class InventarProjektion(private val konfiguration: Configuration)
{

    @CommandHandler
    fun leseInventar(abfrage: LeseInventar): Inventar
    {
        val stream = EreignisLieferant(konfiguration.eventStore())
                .ereignisseVon(abfrage.ausInventur)

        return stream
                .map { m -> alsInventar(m.payload) }
                .reduce({ l, r -> if (r == Inventar.leer) l else r })
                .orElse(Inventar.leer)

    }

    class EreignisLieferant(val eventStore: EventStore){

        fun ereignisseVon(inventur: Aggregatkennung): Stream<out DomainEventMessage<*>>
        {
            return eventStore
                    .readEvents(inventur.toString())
                    .asStream()
        }
    }

    private fun alsInventar(message: Any): Inventar =
            when (message)
            {
                is InventarErfasst -> message.inventar
                else -> Inventar.leer
            }
}
