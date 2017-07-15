package com.github.haschi.haushaltsbuch.domäne;

import com.github.haschi.haushaltsbuch.infrastruktur.Ereignismonitor;
import org.axonframework.eventhandling.EventMessage;

import java.util.LinkedList;
import java.util.Queue;

public class Warteschlangenmonitor implements Ereignismonitor
{
    Queue<EventMessage<?>> events = new LinkedList<>();

    @Override
    public void ereignisEingetreten(final EventMessage<?> ereignis)
    {
        events.add(ereignis);
    }

    @Override
    public Object nächstesEreignis() throws InterruptedException
    {
        return events.remove().getPayload();
    }
}
