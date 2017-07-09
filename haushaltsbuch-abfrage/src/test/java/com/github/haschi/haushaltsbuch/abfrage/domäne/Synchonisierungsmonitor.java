package com.github.haschi.haushaltsbuch.abfrage.domäne;

import org.axonframework.messaging.Message;
import org.axonframework.monitoring.MessageMonitor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.SynchronousQueue;
import java.util.concurrent.TimeUnit;
import java.util.function.Consumer;
import java.util.function.Predicate;

/**
 * Synchronisiert Tests mit TrackingEventProcessor des zu
 * testenden Systems.
 *
 * Eine Instanz des Synchronisierungsmonitors muss einem
 * TrackingEventProcessor in der Axon Konfiguration des
 * zu testenden Systems zugewiesen werden. Der Testclient
 * muss auf dieser Instanz warten aufrufen nachdem dieser
 * eine Operation angestoßen hat, die eine Nachricht auf
 * den Eventbus des Zielsystems legt. Erst danach kann
 * sichergestellt werden, dass die Nachricht auf dem
 * Zielsystem verarbeitet worden ist.
 */
public class Synchonisierungsmonitor implements MessageMonitor<Message<?>>
{
    private static final Logger log = LoggerFactory.getLogger(Synchonisierungsmonitor.class);

    private final SynchronousQueue<Message<?>> successQueue = new SynchronousQueue<>(true);

    public Synchonisierungsmonitor() {
        log.info("Synchronisierungsmonitor erzeugt");
    }

    public void warten(Consumer<Message<?>> messageConsumer) throws SynchronisationFehlgeschlagen
    {
        try
        {
            messageConsumer.accept(successQueue.take());
        } catch (InterruptedException e)
        {
            throw new SynchronisationFehlgeschlagen(e);
        }
    }

    public void erwarte(Predicate<Message<?>> predicate, Consumer<InterruptedException> errorHandler)
    {
        log.info("warten");

        try
        {
            predicate.test(successQueue.take());
        } catch (InterruptedException e)
        {
            errorHandler.accept(e);
        }
    }

    public void erwarte(Class<?> clazz, Consumer<InterruptedException> errorHandler)
    {
        erwarte(message -> clazz.isAssignableFrom(message.getPayloadType()), errorHandler);
    }

    @Override
    public MonitorCallback onMessageIngested(Message<?> message)
    {
        return new MonitorCallback()
        {
            @Override
            public void reportSuccess()
            {
                try
                {
                    log.info("Ereignis verarbeitet: "
                        + message.getPayloadType().getSimpleName()
                        + " "
                        + message.getPayload().toString());

                    successQueue.offer(message, 1L, TimeUnit.SECONDS);
                }
                catch (Exception exception)
                {
                    log.error("Synchronisierungsfehler: ", exception);
                }
            }

            @Override
            public void reportFailure(Throwable cause)
            {

            }

            @Override
            public void reportIgnored()
            {

            }
        };
    }

    public void zurücksetzen()
    {
        successQueue.clear();
    }
}
