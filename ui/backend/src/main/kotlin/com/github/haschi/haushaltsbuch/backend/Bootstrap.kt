package com.github.haschi.haushaltsbuch.backend

import com.fasterxml.jackson.databind.ObjectMapper
import com.github.haschi.dominium.haushaltsbuch.core.application.Anwendung
import com.github.haschi.dominium.haushaltsbuch.core.application.Anwendungskonfiguration
import com.github.haschi.haushaltsbuch.infrastruktur.BackendInfrastrukturFactory
import com.mongodb.MongoClient
import com.mongodb.ServerAddress
import org.axonframework.commandhandling.gateway.CommandGateway
import org.axonframework.mongo.DefaultMongoTemplate
import org.axonframework.queryhandling.QueryGateway
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class Bootstrap
{
    @Autowired
    private lateinit var mapper: ObjectMapper

    @Bean
    fun anwendung(): Anwendung
    {
        logger.info("Anwendungskern starten")

        // TODO: Profilspezifische Konfiguration
        val server = ServerAddress("localhost", 27017)
        val client = MongoClient(server)
        val template = DefaultMongoTemplate(client)

        logger.info("Service gestartet")
        val factory = BackendInfrastrukturFactory(mapper, template)
        val konfiguration = Anwendungskonfiguration(factory)
        return konfiguration.start { Anwendung(it) }
    }

    @Bean
    fun commandGateway(): CommandGateway
    {
        return anwendung().api().command
    }

    @Bean
    fun queryGateway(): QueryGateway
    {
        return anwendung().api().query
    }

    companion object
    {
        val logger: Logger = LoggerFactory.getLogger(Bootstrap::class.java)
    }
}