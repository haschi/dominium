package com.github.haschi.haushaltsbuch

import com.github.haschi.haushaltsbuch.backend.exit
import com.github.haschi.haushaltsbuch.backend.main
import cucumber.api.java.After
import cucumber.api.java.Before
import org.slf4j.Logger
import org.slf4j.LoggerFactory

class SystemHook
{
    @Before("@system")
    fun serviceStarten()
    {
        logger.info("Service gestartet")
        main(emptyArray())
    }

    @After("@system")
    fun serviceStoppen()
    {
        logger.info("Service gestoppt")
        exit()
    }

    companion object
    {
        val logger: Logger = LoggerFactory.getLogger(SystemHook::class.java)
    }
}