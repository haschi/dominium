package com.github.haschi.haushaltsbuch.rest;

import com.github.haschi.haushaltsbuch.infrastruktur.Ereignismonitor;
import org.axonframework.eventhandling.EventMessage;
import org.axonframework.messaging.Message;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.LinkedTransferQueue;
import java.util.concurrent.TransferQueue;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.fail;

public final class Synchronisierungsmonitor implements Ereignismonitor
{
    private static final Logger log = LoggerFactory.getLogger(Synchronisierungsmonitor.class);

    private final TransferQueue<EventMessage<?>> queue = new LinkedTransferQueue<>();
    private final List<EventMessage<?>> ereignisse = new ArrayList<>();

    @Override
    public void ereignisEingetreten(final EventMessage<?> any)
    {
        log.info("Ereignis eingetreten: " + any.getPayloadType().getSimpleName());
        queue.add(any);
    }

    @Override
    public void erwarte(final int anzahlEreignisse)
    {
        ereignisse.clear();
        int zähler = anzahlEreignisse;
        try
        {
            while (zähler-- > 0)
            {
                ereignisse.add(queue.take());
            }
        }
        catch (final InterruptedException exception)
        {
            fail(exception.getLocalizedMessage());
        }
    }

    @Override
    public List<Object> erwarteteEreignisse()
    {
        return ereignisse.stream().map(Message::getPayload).collect(Collectors.toList());
    }

    @Override
    public Object nächstesEreignis() throws InterruptedException
    {
        final EventMessage<?> message = queue.take();
        log.info("Nächstes Ereignis: " + message.getPayloadType().getSimpleName());

        return message.getPayload();
    }
}
