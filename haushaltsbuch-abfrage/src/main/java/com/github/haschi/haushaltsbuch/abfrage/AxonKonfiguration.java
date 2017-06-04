package com.github.haschi.haushaltsbuch.abfrage;

import org.axonframework.config.Configuration;
import org.axonframework.config.DefaultConfigurer;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.Produces;

@ApplicationScoped
public class AxonKonfiguration
{
    @Produces
    @ApplicationScoped
    public Configuration konfigurieren() {
        return DefaultConfigurer.defaultConfiguration()
                .buildConfiguration();
    }
}
