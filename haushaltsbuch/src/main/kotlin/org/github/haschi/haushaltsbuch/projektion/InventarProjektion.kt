package org.github.haschi.haushaltsbuch.projektion

import org.axonframework.commandhandling.CommandHandler
import org.axonframework.config.Configuration
import org.github.haschi.haushaltsbuch.api.InventarErfasst
import org.github.haschi.haushaltsbuch.api.LeseInventar
import org.github.haschi.haushaltsbuch.core.Inventar

class InventarProjektion(private val konfiguration: Configuration)
{

    @CommandHandler
    fun leseInventar(abfrage: LeseInventar): Inventar
    {
        val eventStore = konfiguration.eventStore()
        val eventStream = eventStore.readEvents(abfrage.ausInventur.toString())

        return eventStream.asStream()
                .map { m -> alsInventar(m.payload) }
                .reduce({ l, r -> if (r == Inventar.leer) l else r })
                .orElse(Inventar.leer)

    }

    private fun alsInventar(message: Any): Inventar =
            when (message)
            {
                is InventarErfasst -> message.inventar
                else -> Inventar.leer
            }
}
