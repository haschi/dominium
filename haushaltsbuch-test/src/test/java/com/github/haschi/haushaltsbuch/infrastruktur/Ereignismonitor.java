package com.github.haschi.haushaltsbuch.infrastruktur;

import org.axonframework.eventhandling.EventMessage;

import java.util.List;
import java.util.function.Consumer;

public interface Ereignismonitor
{
    void ereignisEingetreten(EventMessage<?> any);

    void erwarte(int anzahlEreignisse);

    List<Object> erwarteteEreignisse();

    void erwartetesEreignis(int position, Consumer<Object> assertion);

    Object nächstesEreignis() throws InterruptedException;
}
