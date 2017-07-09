package com.github.haschi.haushaltsbuch.rest;

import org.axonframework.eventhandling.EventHandler;
import org.axonframework.eventhandling.EventMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TestEventHandler
{
    private static final Logger log = LoggerFactory.getLogger(TestEventHandler.class);

    private Ereignismonitor monitor;

    TestEventHandler(Ereignismonitor monitor) {
        this.monitor = monitor;
    }

    @EventHandler
    public void falls(EventMessage<?> any) {
        monitor.ereignisEingetreten(any);
    }
}
