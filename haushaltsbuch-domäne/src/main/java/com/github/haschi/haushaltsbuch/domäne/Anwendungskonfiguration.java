package com.github.haschi.haushaltsbuch.domäne;

import org.axonframework.config.Configurer;
import org.axonframework.config.DefaultConfigurer;

public class Anwendungskonfiguration implements Konfigurationsschritt
{
    @Override
    public Configurer konfigurieren() throws Exception
    {
        return DefaultConfigurer.defaultConfiguration()
                .registerCommandHandler(config -> new Befehlshandler())
                .configureAggregate(Haushaltsbuch.class);
    }
}
