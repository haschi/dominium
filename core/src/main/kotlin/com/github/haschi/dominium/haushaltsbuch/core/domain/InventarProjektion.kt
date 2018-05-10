package com.github.haschi.dominium.haushaltsbuch.core.domain

import com.github.haschi.dominium.haushaltsbuch.core.model.events.InventarErfasst
import com.github.haschi.dominium.haushaltsbuch.core.model.events.InventurBeendet
import com.github.haschi.dominium.haushaltsbuch.core.model.queries.LeseInventar
import com.github.haschi.dominium.haushaltsbuch.core.model.values.Inventar
import com.github.haschi.dominium.haushaltsbuch.core.model.values.Schulden
import com.github.haschi.dominium.haushaltsbuch.core.model.values.Vermoegenswerte
import org.axonframework.queryhandling.QueryHandler
import java.time.LocalDateTime

class InventarProjektion(private val vergangenheit: Historie)
{
    @QueryHandler
    fun leseInventar(abfrage: LeseInventar): Inventar
    {
        // TODO("Empty sequence can't be reduced")
        return vergangenheit.bezüglich(abfrage.id)
                .map { m -> alsInventar(m.payload) }
                .reduce({ l, r -> reduce(l, r) })
//                .
//                .orElse(Inventar.leer)

    }

    private fun reduce(left: Inventar, right: Inventar): Inventar
    {
        return Inventar(
                 maxOf(left.erstelltAm, right.erstelltAm),
                 Vermoegenswerte(left.anlagevermoegen + right.anlagevermoegen),
                Vermoegenswerte(left.umlaufvermoegen + right.umlaufvermoegen),
                Schulden(left.schulden + right.schulden))
    }

    private fun alsInventar(message: Any): Inventar =
            when (message)
            {
                is InventurBeendet -> Inventar(message.beendetAm, Inventar.leer.anlagevermoegen, Inventar.leer.umlaufvermoegen, Inventar.leer.schulden)
                is InventarErfasst -> Inventar(LocalDateTime.MIN, message.anlagevermoegen, message.umlaufvermoegen, message.schulden)
                else -> Inventar.leer
            }
}
