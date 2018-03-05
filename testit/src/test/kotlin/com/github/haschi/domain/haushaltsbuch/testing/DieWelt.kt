package com.github.haschi.domain.haushaltsbuch.testing

import com.github.haschi.dominium.haushaltsbuch.core.application.Anwendung
import com.github.haschi.dominium.haushaltsbuch.core.application.Anwendungskonfiguration
import com.github.haschi.dominium.haushaltsbuch.core.application.HaushaltsbuchführungApi
import com.github.haschi.dominium.haushaltsbuch.core.application.InventurApi
import com.github.haschi.dominium.haushaltsbuch.core.domain.Historie
import com.github.haschi.dominium.haushaltsbuch.core.model.values.Aggregatkennung
import org.axonframework.queryhandling.QueryGateway
// import org.picocontainer.Startable
import java.util.concurrent.CompletableFuture
import javax.management.Query

class DieWelt(private val domäne: Anwendungskonfiguration) /*: Startable*/
{

    var anwendung: Anwendung? = null

    //override
    fun stop()
    {
        println("DieWelt stop")
        anwendung!!.stop()
    }

    // override
    fun start()
    {
        println("DieWelt start")
        anwendung = domäne.start { Anwendung(it) }
    }

    val query: QueryGateway
            get() = anwendung!!.api().query

    val inventur: InventurApi
            get() = anwendung!!.api().inventur

    val haushaltsbuchfuehrung: HaushaltsbuchführungApi
            get() = anwendung!!.api().haushaltsbuch

    val historie: Historie
        get() = anwendung!!.api().historie

    var aktuelleInventur: Aggregatkennung = Aggregatkennung.nil
    var intention: CompletableFuture<Any>? = null
    var aktuellesHaushaltsbuch: Aggregatkennung = Aggregatkennung.nil
}
