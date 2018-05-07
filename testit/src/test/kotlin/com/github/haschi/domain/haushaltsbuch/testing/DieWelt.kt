package com.github.haschi.domain.haushaltsbuch.testing

import com.github.haschi.dominium.haushaltsbuch.core.application.Anwendung
import com.github.haschi.dominium.haushaltsbuch.core.application.Anwendungskonfiguration
import com.github.haschi.dominium.haushaltsbuch.core.application.HaushaltsbuchführungApi
import com.github.haschi.dominium.haushaltsbuch.core.application.InventurApi
import com.github.haschi.dominium.haushaltsbuch.core.domain.Historie
import com.github.haschi.dominium.haushaltsbuch.core.model.values.Aggregatkennung
import com.github.haschi.haushaltsbuch.infrastruktur.Testzeit
import org.axonframework.queryhandling.QueryGateway
import org.picocontainer.Startable
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import java.time.LocalDateTime
import java.util.concurrent.CompletableFuture
import kotlin.properties.Delegates

class DieWelt(private val domäne: Anwendungskonfiguration) : Startable
{
    private var anwendung: Anwendung? = null

    override
    fun stop()
    {
        anwendung!!.stop()
        logger.info("Welt gestoppt")
    }

    override
    fun start()
    {
        anwendung = domäne.start { Anwendung(it) }
        logger.info("Welt gestartet")
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
    var zeit: Testzeit = Testzeit

    companion object
    {
        val logger: Logger = LoggerFactory.getLogger(DieWelt::class.java)
    }
}
