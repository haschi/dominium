package com.github.haschi.domain.haushaltsbuch.projektion

import com.github.haschi.domain.haushaltsbuch.modell.core.queries.LeseInventar
import com.github.haschi.domain.haushaltsbuch.modell.core.values.Inventar
import org.axonframework.commandhandling.CommandHandler

class InventarProjektion(private val vergangenheit: VergangeneEreignisse)
{
    @CommandHandler
    fun leseInventar(abfrage: LeseInventar): Inventar
    {

        return vergangenheit.bezüglich(abfrage.ausInventur)
                .map { m -> alsInventar(m.payload) }
                .reduce( { l, r -> if (r == Inventar.leer) l else r } )
//                .
//                .orElse(Inventar.leer)

    }

    private fun alsInventar(message: Any): Inventar =
            when (message)
            {
                is com.github.haschi.domain.haushaltsbuch.modell.core.events.InventarErfasst -> message.inventar
                else -> Inventar.leer
            }
}
