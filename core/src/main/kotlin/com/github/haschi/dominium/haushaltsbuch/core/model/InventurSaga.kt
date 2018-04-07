package com.github.haschi.dominium.haushaltsbuch.core.model

import com.github.haschi.dominium.haushaltsbuch.core.model.commands.SchlageEröffnungsbilanzVor
import com.github.haschi.dominium.haushaltsbuch.core.model.events.InventurBeendet
import com.github.haschi.dominium.haushaltsbuch.core.model.queries.LeseInventar
import com.github.haschi.dominium.haushaltsbuch.core.model.values.Aggregatkennung
import com.github.haschi.dominium.haushaltsbuch.core.model.values.Inventar
import org.axonframework.commandhandling.gateway.CommandGateway
import org.axonframework.eventhandling.saga.SagaEventHandler
import org.axonframework.eventhandling.saga.SagaLifecycle
import org.axonframework.eventhandling.saga.StartSaga
import org.axonframework.queryhandling.QueryBus
import org.axonframework.queryhandling.QueryGateway
import java.util.concurrent.CompletableFuture

class InventurSaga
{
    @StartSaga
    @SagaEventHandler(associationProperty = "inventurId")
    fun falls(ereignis: InventurBeendet, queryGateway: QueryGateway, commandGateway: CommandGateway)
    {
        val query = LeseInventar(ereignis.inventurId)
        val inventar = queryGateway.query(query, Inventar::class.java).get()

        val anweisung = SchlageEröffnungsbilanzVor(Aggregatkennung.neu(), ereignis.inventurId, inventar)
        try
        {
            commandGateway.sendAndWait<SchlageEröffnungsbilanzVor>(anweisung)
        }
        catch (ausnahme: Exception)
        {
            println(ausnahme.message)
        }


        SagaLifecycle.end()
    }
}