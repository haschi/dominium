package com.github.haschi.haushaltsbuch.rest;

import com.github.haschi.haushaltsbuch.infrastruktur.Ereignismonitor;
import org.apache.commons.lang3.NotImplementedException;
import org.axonframework.eventhandling.EventMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.concurrent.LinkedTransferQueue;
import java.util.concurrent.TransferQueue;

public final class Synchronisierungsmonitor implements Ereignismonitor
{
    private static final Logger log = LoggerFactory.getLogger(Synchronisierungsmonitor.class);

    private final TransferQueue<EventMessage<?>> queue = new LinkedTransferQueue<>();

    @Override
    public void ereignisEingetreten(final EventMessage<?> any)
    {
        log.info("Ereignis eingetreten: " + any.getPayloadType().getSimpleName());
        queue.add(any);
    }

    @Override
    public void erwarte(final int anzahlEreignisse)
    {
        throw new NotImplementedException("Nicht implementiert");
    }

    @Override
    public List<Object> erwarteteEreignisse()
    {
        throw new NotImplementedException("Nicht implementiert");
    }

    @Override
    public Object nächstesEreignis() throws InterruptedException
    {
        final EventMessage<?> message = queue.take();
        log.info("Nächstes Ereignis: " + message.getPayloadType().getSimpleName());

        return message.getPayload();
    }
}
