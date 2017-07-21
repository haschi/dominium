package com.github.haschi.haushaltsbuch.domäne;

import com.github.haschi.haushaltsbuch.infrastruktur.Ereignismonitor;
import org.axonframework.eventhandling.EventMessage;
import org.axonframework.messaging.Message;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.stream.Collectors;

public class Warteschlangenmonitor implements Ereignismonitor
{
    private final Queue<EventMessage<?>> events = new LinkedList<>();
    private final List<EventMessage<?>> erwartet = new ArrayList<>();

    @Override
    public void ereignisEingetreten(final EventMessage<?> ereignis)
    {
        events.add(ereignis);
    }

    @Override
    public void erwarte(final int anzahlEreignisse)
    {
        erwartet.clear();
        int zähler = anzahlEreignisse;
        while(zähler-- > 0)
        {
            erwartet.add(events.remove());
        }
    }

    @Override
    public List<Object> erwarteteEreignisse()
    {
        return erwartet.stream()
                .map(Message::getPayload)
                .collect(Collectors.toList());
    }

    @Override
    public Object nächstesEreignis() throws InterruptedException
    {
        return events.remove().getPayload();
    }
}
