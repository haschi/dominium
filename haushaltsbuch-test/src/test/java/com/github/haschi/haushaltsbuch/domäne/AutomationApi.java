package com.github.haschi.haushaltsbuch.domäne;

import com.github.haschi.haushaltsbuch.AbstractAutomationApi;
import com.github.haschi.haushaltsbuch.AbstractHaushaltsbuchführungSteps;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.function.Consumer;

public class AutomationApi implements AbstractAutomationApi
{
    Logger log = LoggerFactory.getLogger(AutomationApi.class);

    private HaushaltsbuchführungSteps haushaltsbuchführungApi = new HaushaltsbuchführungSteps();

    public AutomationApi() {
        log.info("Construct");
    }

    @Override
    public void haushaltsbuchführung(Consumer<AbstractHaushaltsbuchführungSteps> consumer)
    {
        consumer.accept(haushaltsbuchführungApi);
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
