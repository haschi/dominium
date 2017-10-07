package com.github.haschi.haushaltsbuch.infrastruktur;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.logging.Logger;
import io.vertx.core.logging.LoggerFactory;
import org.axonframework.config.Configuration;
import org.axonframework.config.DefaultConfigurer;

import java.io.IOException;
import java.io.InputStream;
import java.text.MessageFormat;
import java.util.Properties;

import org.axonframework.eventsourcing.eventstore.inmemory.InMemoryEventStorageEngine;
import org.github.haschi.haushaltsbuch.modell.Inventur;

public class RestApi extends AbstractVerticle
{
    Logger log = LoggerFactory.getLogger(RestApi.class);

    @Override
    public void start() {

//        final Configuration axon = DefaultConfigurer.defaultConfiguration()
//                .configureEmbeddedEventStore(konfiguration -> new InMemoryEventStorageEngine())
//                .configureAggregate(Inventur.class)
//                .buildConfiguration();
//
//        axon.start();

        vertx.createHttpServer()
                .requestHandler(req -> {
                    try
                    {
                        req.response().end(
                                MessageFormat.format("{0} {1}",
                                                     getServiceProperty("service.name"),
                                                     getServiceProperty("service.version")));

                    }
                    catch(final IOException ausnahme) {
                        req.response()
                                .setStatusCode(500)
                                .end("Interner Server Fehler: " + ausnahme.getMessage());
                    }
                })
                .listen(8080);

        log.info("HTTP Server verfügbar auf Port 8080");
    }

    public static String getServiceProperty(final String property) throws IOException
    {
        final ClassLoader classLoader = RestApi.class.getClassLoader();
        try (final InputStream resourceAsStream = classLoader.getResourceAsStream("config.properties"))
        {

            final Properties prop = new Properties();
            prop.load(resourceAsStream);
            return prop.getProperty(property);
        }
    }
}
