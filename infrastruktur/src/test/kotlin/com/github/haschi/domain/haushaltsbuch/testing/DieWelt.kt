package com.github.haschi.domain.haushaltsbuch.testing

import com.github.haschi.dominium.haushaltsbuch.core.application.Anwendungskonfiguration
import com.github.haschi.dominium.haushaltsbuch.core.application.HaushaltsbuchführungApi
import com.github.haschi.dominium.haushaltsbuch.core.application.InventurApi
import com.github.haschi.dominium.haushaltsbuch.core.model.values.Aggregatkennung
import org.picocontainer.Startable
import java.util.concurrent.CompletableFuture

class DieWelt(private val domäne: Anwendungskonfiguration) : Startable {

    override fun stop()
    {
        domäne.stop()
    }

    override fun start()
    {
        domäne.start()
    }

    val query = domäne.api()
    val inventur: InventurApi = domäne.api().inventur
    val haushaltsbuchführung: HaushaltsbuchführungApi = domäne.api().haushaltsbuch

//    fun <T, R> query(abfrage: T, klasse: Class<R>): CompletableFuture<R>{
//        return domäne.queryGateway.send(abfrage, klasse)
//    }

    var aktuelleInventur: Aggregatkennung = Aggregatkennung.nil
    var intention: CompletableFuture<Any>? = null
    var aktuellesHaushaltsbuch: Aggregatkennung = Aggregatkennung.nil
}
