package com.github.haschi.haushaltsbuch.rest;

import com.github.haschi.haushaltsbuch.infrastruktur.Ereignismonitor;
import org.axonframework.eventhandling.EventMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.SynchronousQueue;

public final class Synchronisierungsmonitor implements Ereignismonitor
{
    private static final Logger log = LoggerFactory.getLogger(Synchronisierungsmonitor.class);

    private final SynchronousQueue<EventMessage<?>> queue = new SynchronousQueue<>(true);

    @Override
    public void ereignisEingetreten(final EventMessage<?> any)
    {
        log.info("Ereignis eingetreten: " + any.getPayloadType().getSimpleName());
        queue.add(any);
    }

    @Override
    public Object nächstesEreignis() throws InterruptedException
    {
        final EventMessage<?> message = queue.take();
        log.info("Nächstes Ereignis: " + message.getPayloadType().getSimpleName());

        return message.getPayload();
    }
}
