package com.github.haschi.haushaltsbuch.infrastruktur;

import org.axonframework.eventhandling.EventMessage;

public interface Ereignismonitor
{
    void ereignisEingetreten(EventMessage<?> any);

    Object nächstesEreignis() throws InterruptedException;
}
