package com.github.haschi.haushaltsbuch.rest;

import org.axonframework.eventhandling.EventMessage;
import org.axonframework.messaging.Message;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.SynchronousQueue;
import java.util.function.Consumer;
import java.util.function.Predicate;

public class Ereignismonitor
{
    private static final Logger log = LoggerFactory.getLogger(Ereignismonitor.class);

    private final SynchronousQueue<EventMessage<?>> queue = new SynchronousQueue<>(true);

    public void ereignisEingetreten(EventMessage<?> any)
    {
        log.info("Ereignis eingetreten: " + any.getPayloadType().getSimpleName());
        queue.add(any);
    }

    public boolean erwartet(Predicate<Message<?>> prodicate, Consumer<InterruptedException> errorHandler)
    {
        try
        {
            return prodicate.test(queue.take());
        }
        catch (InterruptedException exception)
        {
            errorHandler.accept(exception);
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
