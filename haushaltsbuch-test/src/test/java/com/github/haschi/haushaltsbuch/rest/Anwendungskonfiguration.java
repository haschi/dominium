package com.github.haschi.haushaltsbuch.rest;

import com.github.haschi.haushaltsbuch.domäne.Konfigurationsschritt;
import org.axonframework.config.Configurer;
import org.axonframework.config.DefaultConfigurer;
import org.axonframework.config.EventHandlingConfiguration;

public class Anwendungskonfiguration implements Konfigurationsschritt
{
    private Ereignismonitor ereignismonitor;

    public Anwendungskonfiguration(Ereignismonitor ereignismonitor) {

        this.ereignismonitor = ereignismonitor;
    }

    @Override
    public Configurer konfigurieren() throws Exception
    {
        EventHandlingConfiguration testhandler = new EventHandlingConfiguration()
                .registerEventHandler(config -> new TestEventHandler(ereignismonitor))
                .usingTrackingProcessors();

        return DefaultConfigurer.defaultConfiguration()
                .registerModule(testhandler);
    }
}
