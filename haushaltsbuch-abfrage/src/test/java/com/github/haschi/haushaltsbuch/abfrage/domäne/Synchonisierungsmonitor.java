package com.github.haschi.haushaltsbuch.abfrage.domäne;

import org.axonframework.messaging.Message;
import org.axonframework.monitoring.MessageMonitor;

import java.util.concurrent.SynchronousQueue;
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
class Synchonisierungsmonitor implements MessageMonitor<Message<?>>
{

    private final SynchronousQueue<Message<?>> successQueue = new SynchronousQueue<>();

    public void warten(Consumer<Message<?>> messageConsumer) throws InterruptedException
    {
        messageConsumer.accept(successQueue.take());
    }

    public void erwarte(Predicate<Message<?>> predicate) throws InterruptedException
    {
        predicate.test(successQueue.take());
    }

    @Override
    public MonitorCallback onMessageIngested(Message<?> message)
    {
        return new MonitorCallback()
        {
            @Override
            public void reportSuccess()
            {
                successQueue.add(message);
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
}
