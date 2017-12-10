package com.github.haschi.domain.haushaltsbuch.testing

import com.github.haschi.domain.haushaltsbuch.modell.core.values.Aggregatkennung
import com.github.haschi.haushaltsbuch.infrastruktur.Domänenkonfiguration
import com.github.haschi.haushaltsbuch.infrastruktur.EreignisLieferant
import com.github.haschi.dominium.haushaltsbuch.core.application.HaushaltsbuchführungApi
import com.github.haschi.dominium.haushaltsbuch.core.application.InventurApi
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

    val inventur: InventurApi = domäne.gateway(InventurApi::class)
    val haushaltsbuchführung: HaushaltsbuchführungApi = domäne.gateway(HaushaltsbuchführungApi::class)
    val vergangenheit: EreignisLieferant = domäne.historie

    fun <T, R> query(abfrage: T, klasse: Class<R>): CompletableFuture<R>{
        return domäne.queryGateway.send(abfrage, klasse)
    }

    var aktuelleInventur: Aggregatkennung = Aggregatkennung.nil
    var intention: CompletableFuture<Any>? = null
    var aktuellesHaushaltsbuch: Aggregatkennung = Aggregatkennung.nil
}
