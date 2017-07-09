package com.github.haschi.haushaltsbuch.rest;

import org.axonframework.eventhandling.EventHandler;
import org.axonframework.eventhandling.EventMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public final class TestEventHandler
{
    private static final Logger log = LoggerFactory.getLogger(TestEventHandler.class);

    private final Ereignismonitor monitor;

    TestEventHandler(final Ereignismonitor monitor)
    {
        this.monitor = monitor;
    }

    @EventHandler
    public void falls(final EventMessage<?> any)
    {
        monitor.ereignisEingetreten(any);
    }
}
