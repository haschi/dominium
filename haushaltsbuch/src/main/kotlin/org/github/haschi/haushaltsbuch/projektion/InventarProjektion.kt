package org.github.haschi.haushaltsbuch.projektion

import org.axonframework.commandhandling.CommandHandler
import org.axonframework.config.Configuration
import org.github.haschi.haushaltsbuch.modell.core.events.InventarErfasst
import org.github.haschi.haushaltsbuch.modell.core.queries.LeseInventar
import org.github.haschi.haushaltsbuch.modell.core.values.Inventar

class InventarProjektion(private val konfiguration: Configuration, private val lieferant: IEreignisLieferant)
{
    @CommandHandler
    fun leseInventar(abfrage: LeseInventar): Inventar
    {
        val stream = lieferant
                .ereignisseVon(abfrage.ausInventur)

        return stream
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
