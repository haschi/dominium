package com.github.haschi.haushaltsbuch.backend

import com.github.haschi.dominium.haushaltsbuch.core.application.Anwendung
import com.github.haschi.dominium.haushaltsbuch.core.application.Anwendungskonfiguration
import com.github.haschi.haushaltsbuch.infrastruktur.AxonInfrastrukturFactory
import org.axonframework.commandhandling.gateway.CommandGateway
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class Bootstrap
{
    @Bean
    fun anwendung(): Anwendung
    {
        logger.info("Anwendungskern starten")

        val factory = AxonInfrastrukturFactory()
        val konfiguration = Anwendungskonfiguration(factory)
        return konfiguration.start { Anwendung(it) }

    }

    @Bean
    fun commandGateway(): CommandGateway
    {
        return anwendung().api().command
    }

    companion object
    {
        val logger: Logger = LoggerFactory.getLogger(Bootstrap::class.java)
    }
}