package com.github.haschi.haushaltsbuch.rest;

import com.github.haschi.haushaltsbuch.AbstractAutomationApi;
import com.github.haschi.haushaltsbuch.AbstractHaushaltsbuchführungSteps;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.function.Consumer;

public class AutomationApi implements AbstractAutomationApi
{
    private Logger log = LoggerFactory.getLogger(AutomationApi.class);

    @Override
    public void haushaltsbuchführung(Consumer<AbstractHaushaltsbuchführungSteps> consumer)
    {

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
