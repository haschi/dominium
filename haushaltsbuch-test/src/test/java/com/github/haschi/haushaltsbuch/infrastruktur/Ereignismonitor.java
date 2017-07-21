package com.github.haschi.haushaltsbuch.infrastruktur;

import org.axonframework.eventhandling.EventMessage;

import java.util.List;

public interface Ereignismonitor
{
    void ereignisEingetreten(EventMessage<?> any);

    void erwarte(int anzahlEreignisse);

    List<Object> erwarteteEreignisse();

    Object nächstesEreignis() throws InterruptedException;
}
