package com.github.haschi.haushaltsbuch.backend.rest.api

import com.github.haschi.dominium.haushaltsbuch.core.application.Anwendungskonfiguration
import org.slf4j.LoggerFactory
import javax.annotation.PreDestroy

class GestarteteAnwendung(private val anwendung: Anwendungskonfiguration)
{
    val gateway = anwendung.api().command

    @PreDestroy
    fun shutdown()
    {
        logger.info("Anwendungskern wird beendet")
        anwendung.stop()
        logger.info("ENDE")
    }

    companion object
    {
        private val logger = LoggerFactory.getLogger(GestarteteAnwendung::class.java)
    }
}
