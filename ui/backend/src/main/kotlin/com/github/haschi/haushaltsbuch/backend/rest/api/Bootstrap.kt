package com.github.haschi.haushaltsbuch.backend.rest.api

import com.github.haschi.dominium.haushaltsbuch.core.application.Anwendungskonfiguration
import com.github.haschi.haushaltsbuch.infrastruktur.AxonInfrastrukturFactory
import org.axonframework.commandhandling.gateway.CommandGateway
import org.slf4j.LoggerFactory
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class Bootstrap
{
    @Bean
    fun anwendungskonfiguration(): GestarteteAnwendung
    {
        logger.info("Anwendungkern starten")

        val factory = AxonInfrastrukturFactory()
        val konfiguration = Anwendungskonfiguration(factory)
        konfiguration.start()

        return GestarteteAnwendung(konfiguration)
    }

    @Bean
    fun commandgateway(): CommandGateway
    {
        return anwendungskonfiguration().gateway
    }

    companion object
    {
        val logger = LoggerFactory.getLogger(Bootstrap::class.java)
    }
}