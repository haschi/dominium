package com.github.haschi.haushaltsbuch.infrastruktur

import com.github.haschi.dominium.haushaltsbuch.core.application.Anwendungskonfiguration
import cucumber.runtime.java.picocontainer.PicoFactory
import org.slf4j.LoggerFactory

class SystemtestPicoFactory : PicoFactory()
{
    init
    {
        logger.info("Initialisiere Systemtest Pico Factory")
    }

    companion object
    {
        val logger = LoggerFactory.getLogger(SystemtestPicoFactory::class.java)
    }
}
