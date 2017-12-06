package com.github.haschi.domain.haushaltsbuch.projektion

import com.github.haschi.domain.haushaltsbuch.modell.core.events.InventarErfasst
import com.github.haschi.domain.haushaltsbuch.modell.core.queries.LeseInventar
import com.github.haschi.domain.haushaltsbuch.modell.core.values.Inventar
import org.axonframework.queryhandling.QueryHandler

class InventarProjektion(private val vergangenheit: Historie)
{
    @QueryHandler
    fun leseInventar(abfrage: LeseInventar): Inventar
    {

        // TODO("Empty sequence can't be reduced")
        return vergangenheit.bezüglich(abfrage.ausInventur)
                .map { m -> alsInventar(m.payload) }
                .reduce( { l, r -> if (r == Inventar.leer) l else r } )
//                .
//                .orElse(Inventar.leer)

    }

    private fun alsInventar(message: Any): Inventar =
            when (message)
            {
                is InventarErfasst -> message.inventar
                else -> Inventar.leer
            }
}
