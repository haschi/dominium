package com.github.haschi.haushaltsbuch.infrastruktur

import cucumber.runtime.java.picocontainer.PicoFactory
import io.vertx.core.logging.LoggerFactory

class SystemtestPicoFactory : PicoFactory()
{
    init
    {
        logger.info("Initialisiere Systemtest Pico Factory")
        addClass(Testcloud::class.java)
        addClass(Welt::class.java)
        addClass(AxonInfrastrukturFactory::class.java)
        addClass(Domänenkonfiguration::class.java)
    }

    companion object
    {
        val logger = LoggerFactory.getLogger(SystemtestPicoFactory::class.java)
    }
}
