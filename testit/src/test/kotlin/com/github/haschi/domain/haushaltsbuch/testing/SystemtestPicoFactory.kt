package com.github.haschi.domain.haushaltsbuch.testing

import cucumber.runtime.java.picocontainer.PicoFactory
import org.slf4j.Logger
import org.slf4j.LoggerFactory

class SystemtestPicoFactory : PicoFactory()
{
    init
    {
        logger.info("Initialisiere Systemtest Pico Factory")
    }

    companion object
    {
        val logger: Logger = LoggerFactory.getLogger(SystemtestPicoFactory::class.java)
    }
}
