package com.github.haschi.haushaltsbuch.rest;

import org.axonframework.eventhandling.EventMessage;
import org.axonframework.messaging.Message;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.SynchronousQueue;
import java.util.function.Consumer;
import java.util.function.Predicate;

public final class Ereignismonitor
{
    private static final Logger log = LoggerFactory.getLogger(Ereignismonitor.class);

    private final SynchronousQueue<EventMessage<?>> queue = new SynchronousQueue<>(true);

    public void ereignisEingetreten(final EventMessage<?> any)
    {
        log.info("Ereignis eingetreten: " + any.getPayloadType().getSimpleName());
        queue.add(any);
    }

    public boolean erwartet(final Predicate<Message<?>> prädikat, final Consumer<InterruptedException> errorHandler)
    {
        try
        {
            return prädikat.test(queue.take());
        }
        catch (final InterruptedException ausnahme)
        {
            errorHandler.accept(ausnahme);
        }

        return false;
    }

    public Object nächstesEreignis() throws InterruptedException
    {
        final EventMessage<?> message = queue.take();
        log.info("Nächstes Ereignis: " + message.getPayloadType().getSimpleName());

        return message.getPayload();
    }
}
