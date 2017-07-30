package com.github.haschi.haushaltsbuch.domäne;

import org.axonframework.config.Configuration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.context.Destroyed;
import javax.enterprise.context.Initialized;
import javax.enterprise.event.Observes;
import javax.enterprise.inject.Produces;
import javax.inject.Inject;

@ApplicationScoped
public class CqrsKonfigurator
{
    private final Systemumgebung systemumgebung;

    private Configuration konfiguration;

    @Inject
    public CqrsKonfigurator(final Systemumgebung systemumgebung)
    {
        this.systemumgebung = systemumgebung;
    }

    public Configuration konfigurieren() throws Exception
    {
        return systemumgebung.konfigurieren();
    }

    public void init(@Observes @Initialized(ApplicationScoped.class) final Object init) {
        try {
            start();
        } catch (Exception e) {
            throw new KonfigurationFehlgeschlagen(e);
        }
    }

    private void start() throws Exception
    {
        assert systemumgebung != null;

        final Configuration konfigurieren = konfigurieren();

        konfigurieren.start();

        this.konfiguration = konfigurieren;
    }

    @Produces
    @ApplicationScoped
    public Configuration getKonfiguration() {
        assert this.konfiguration != null;
        return this.konfiguration;
    }

    public void destroy(@Observes @Destroyed(ApplicationScoped.class) Object init) {
        shutdown();
    }

    public void shutdown()
    {
        if (konfiguration != null) {
            konfiguration.shutdown();
        }
    }
}
