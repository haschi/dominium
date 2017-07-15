package com.github.haschi.haushaltsbuch.domäne;

import com.github.haschi.haushaltsbuch.AbstractAutomationApi;
import com.github.haschi.haushaltsbuch.AbstractHaushaltsbuchführungSteps;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.function.Consumer;

public final class AutomationApi implements AbstractAutomationApi
{
    private static final Logger log = LoggerFactory.getLogger(AutomationApi.class);

    private final HaushaltsbuchführungSteps haushaltsbuchführungApi = new HaushaltsbuchführungSteps();
    private final Systemumgebung testumgebung;

    public AutomationApi(final Systemumgebung testumgebung) {
        this.testumgebung = testumgebung;
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
    }

    @Override
    public void stop()
    {
        log.info("stop");
    }
}
