package com.github.haschi.haushaltsbuch.abfrage;

import org.axonframework.config.Configurer;
import org.axonframework.config.EventHandlingConfiguration;

public class Produktionsumgebung implements Systemumgebung
{
        @Override
        public Configurer konfigurieren(Configurer configurer) throws Exception
        {
            final Haushaltsbuchverzeichnis haushaltsbuchverzeichnis = new Haushaltsbuchverzeichnis();

            EventHandlingConfiguration indexer = new EventHandlingConfiguration()
                    .registerEventHandler(config -> haushaltsbuchverzeichnis)
                    .usingTrackingProcessors();

            return new JgroupsConfigurer(configurer).jgroupsConfiguration("haushaltsbuch")
                    .registerModule(indexer)
                    .registerComponent(Haushaltsbuchverzeichnis.class, config -> haushaltsbuchverzeichnis);

    }
}
