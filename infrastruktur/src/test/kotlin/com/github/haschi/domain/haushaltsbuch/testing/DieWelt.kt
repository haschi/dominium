package com.github.haschi.domain.haushaltsbuch.testing

import com.github.haschi.domain.haushaltsbuch.modell.core.values.Aggregatkennung
import com.github.haschi.haushaltsbuch.infrastruktur.Domänenkonfiguration
import com.github.haschi.haushaltsbuch.infrastruktur.EreignisLieferant
import com.github.haschi.haushaltsbuch.infrastruktur.InventurCommandGateway
import org.picocontainer.Startable
import java.util.concurrent.CompletableFuture

class DieWelt(private val domäne: Domänenkonfiguration) : Startable {

    override fun stop()
    {
        domäne.stop()
    }

    override fun start()
    {
        domäne.start()
    }

    val inventur: InventurCommandGateway =
        domäne.commandGatewayFactory
                .createGateway(InventurCommandGateway::class.java)

    val haushaltsbuchführung: HaushaltsbuchführungCommandGateway =
            domäne.commandGatewayFactory
                    .createGateway(HaushaltsbuchführungCommandGateway::class.java)

    val vergangenheit: EreignisLieferant =
            domäne.vergangenheit;

    fun <T> commands(body: (InventurCommandGateway)-> CompletableFuture<T>): T
    {
        return body(inventur).get()
    }

    fun <T, R> query(abfrage: T, klasse: Class<R>): CompletableFuture<R>{
        return domäne.queryGateway.send(abfrage, klasse)
    }

    var aktuelleInventur: Aggregatkennung? = null
    var intention: CompletableFuture<Any>? = null
    var aktuellesHaushaltsbuch: Aggregatkennung? = null
}
