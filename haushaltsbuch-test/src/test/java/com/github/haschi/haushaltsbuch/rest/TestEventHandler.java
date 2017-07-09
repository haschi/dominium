package com.github.haschi.haushaltsbuch.rest;

import org.axonframework.eventhandling.EventHandler;
import org.axonframework.eventhandling.EventMessage;

public final class TestEventHandler
{
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
