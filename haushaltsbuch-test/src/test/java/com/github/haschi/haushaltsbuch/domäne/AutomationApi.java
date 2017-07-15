package com.github.haschi.haushaltsbuch.domäne;

import com.github.haschi.haushaltsbuch.AbstractAutomationApi;
import com.github.haschi.haushaltsbuch.AbstractHaushaltsbuchführungSteps;
import com.github.haschi.haushaltsbuch.infrastruktur.Ereignismonitor;
import org.assertj.core.api.Assertions;
import org.axonframework.config.Configuration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.function.Consumer;

public final class AutomationApi implements AbstractAutomationApi
{
    private final Logger log = LoggerFactory.getLogger(AutomationApi.class);

    private HaushaltsbuchführungSteps haushaltsbuchführungApi;
    private final Systemumgebung testumgebung;
    private final Ereignismonitor monitor;
    private Configuration konfiguration;

    public AutomationApi(final Systemumgebung testumgebung, final Ereignismonitor monitor) {
        this.testumgebung = testumgebung;
        this.monitor = monitor;
        log.info("Construct");
    }

    @Override
    public void haushaltsbuchführung(final Consumer<AbstractHaushaltsbuchführungSteps> konsument)
    {
        konsument.accept(haushaltsbuchführungApi);
    }

    @Override
    public void start()
    {
        log.info("start");
        try
        {
            final Configuration konfiguration = testumgebung.konfigurieren();
            konfiguration.start();
            this.konfiguration = konfiguration;

            this.haushaltsbuchführungApi  = new HaushaltsbuchführungSteps(konfiguration, monitor);
        }
        catch (final Exception ausnahme)
        {
            log.error("Konfiguration fehlgeschlagen", ausnahme);
            Assertions.fail("Konfiguration fehlgeschlagen", ausnahme);
        }
    }

    @Override
    public void stop()
    {
        log.info("stop");
        konfiguration.shutdown();
    }
}
