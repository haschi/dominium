package com.github.haschi.haushaltsbuch.domäne;

import org.axonframework.config.Configurer;
import org.axonframework.config.DefaultConfigurer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Anwendungskonfiguration implements Konfigurationsschritt
{
    private static Logger log = LoggerFactory.getLogger(Anwendungskonfiguration.class);

    @Override
    public Configurer konfigurieren() throws Exception
    {
        log.info("konfiguriert");

        return DefaultConfigurer.defaultConfiguration()
                .configureAggregate(Haushaltsbuch.class);
    }
}
