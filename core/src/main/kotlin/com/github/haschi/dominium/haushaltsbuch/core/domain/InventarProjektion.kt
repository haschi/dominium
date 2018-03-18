package com.github.haschi.dominium.haushaltsbuch.core.domain

import com.github.haschi.dominium.haushaltsbuch.core.model.events.InventarErfasst
import com.github.haschi.dominium.haushaltsbuch.core.model.queries.LeseInventar
import com.github.haschi.dominium.haushaltsbuch.core.model.values.Inventar
import org.axonframework.queryhandling.QueryHandler

class InventarProjektion(private val vergangenheit: Historie)
{
    @QueryHandler
    fun leseInventar(abfrage: LeseInventar): Inventar
    {

        // TODO("Empty sequence can't be reduced")
        return vergangenheit.bezueglich(abfrage.id)
                .map { m -> alsInventar(m.payload) }
                .reduce({ l, r -> if (r == Inventar.leer) l else r })
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
