package com.github.haschi.haushaltsbuch.abfrage;

import org.axonframework.config.Configurer;
import org.axonframework.config.DefaultConfigurer;
import org.axonframework.config.EventHandlingConfiguration;

/**
 * Created by matthias on 07.07.17.
 */
public class Anwendungskonfiguration implements Konfigurationsschritt
{
    @Override
    public Configurer konfigurieren() throws Exception
    {
        final Haushaltsbuchverzeichnis haushaltsbuchverzeichnis = new Haushaltsbuchverzeichnis();

        EventHandlingConfiguration indexer = new EventHandlingConfiguration()
                .registerEventHandler(config -> haushaltsbuchverzeichnis)
                .usingTrackingProcessors();

        return DefaultConfigurer.defaultConfiguration()
            .registerModule(indexer)
            .registerComponent(Haushaltsbuchverzeichnis.class, config -> haushaltsbuchverzeichnis);
    }
}
