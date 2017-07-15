package com.github.haschi.haushaltsbuch.rest;

import com.github.haschi.haushaltsbuch.domäne.Konfigurationsschritt;
import com.github.haschi.haushaltsbuch.infrastruktur.TestEventHandler;
import org.axonframework.config.Configurer;
import org.axonframework.config.DefaultConfigurer;
import org.axonframework.config.EventHandlingConfiguration;

public final class Anwendungskonfiguration implements Konfigurationsschritt
{
    private final Synchronisierungsmonitor ereignismonitor;

    public Anwendungskonfiguration(final Synchronisierungsmonitor ereignismonitor)
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
