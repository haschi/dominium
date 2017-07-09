package com.github.haschi.haushaltsbuch.rest;

import com.github.haschi.haushaltsbuch.domäne.Konfigurationsschritt;
import org.axonframework.config.Configurer;
import org.axonframework.config.DefaultConfigurer;
import org.axonframework.config.EventHandlingConfiguration;

public final class Anwendungskonfiguration implements Konfigurationsschritt
{
    private final Ereignismonitor ereignismonitor;

    public Anwendungskonfiguration(final Ereignismonitor ereignismonitor)
    {
        this.ereignismonitor = ereignismonitor;
    }

    @Override
    public Configurer konfigurieren() throws Exception
    {
        final EventHandlingConfiguration konfiguration = new EventHandlingConfiguration()
                .registerEventHandler(config -> new TestEventHandler(ereignismonitor))
                .usingTrackingProcessors();

        return DefaultConfigurer.defaultConfiguration()
                .registerModule(konfiguration);
    }
}
