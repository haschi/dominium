package com.github.haschi.domain.haushaltsbuch.testing

import com.github.haschi.dominium.haushaltsbuch.core.application.Anwendungskonfiguration
import cucumber.runtime.java.picocontainer.PicoFactory
import org.slf4j.LoggerFactory

class KomponententestPicoFactory : PicoFactory()
{
    init
    {
        val logger = LoggerFactory.getLogger(KomponententestPicoFactory::class.java)
        logger.info("Initialisiere Komponententest Pico Factory")

        addClass(TestInfrastrukturFactory::class.java)
        addClass(Anwendungskonfiguration::class.java)
    }
}