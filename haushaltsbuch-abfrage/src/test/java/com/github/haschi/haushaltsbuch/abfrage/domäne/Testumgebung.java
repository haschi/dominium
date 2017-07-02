package com.github.haschi.haushaltsbuch.abfrage.domäne;

import com.github.haschi.haushaltsbuch.abfrage.Haushaltsbuchverzeichnis;
import com.github.haschi.haushaltsbuch.abfrage.Systemumgebung;
import org.axonframework.config.Configurer;
import org.axonframework.config.EventHandlingConfiguration;
import org.axonframework.eventsourcing.eventstore.inmemory.InMemoryEventStorageEngine;
import org.axonframework.messaging.Message;
import org.axonframework.monitoring.MessageMonitor;

import java.util.concurrent.SynchronousQueue;
import java.util.function.Consumer;

public class Testumgebung implements Systemumgebung
{

    public Synchonisierer getMonitor()
    {
        return monitor;
    }

    final Synchonisierer monitor = new Synchonisierer();

    class Synchonisierer implements MessageMonitor<Message<?>> {

        private final SynchronousQueue<Message<?>> successQueue = new SynchronousQueue<>();

        public void warten(Consumer<Message<?>> x) throws InterruptedException
        {
            x.accept(successQueue.take());
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

    @Override
    public Configurer konfigurieren(Configurer configurer)
    {
        Haushaltsbuchverzeichnis haushaltsbuchverzeichnis = new Haushaltsbuchverzeichnis();

        // TODO Tracking Event Processor für den Indexer verwenden.

        EventHandlingConfiguration indexer = new EventHandlingConfiguration()
                .registerEventHandler(config -> haushaltsbuchverzeichnis)
                .usingTrackingProcessors();

//                .registerEventProcessorFactory((conf, name, handlers) ->
//                    new TrackingEventProcessor(
//                        name,
//                        new SimpleEventHandlerInvoker(
//                                Collections.singletonList (haushaltsbuchverzeichnis),
//                                conf.parameterResolverFactory(), conf.getComponent(
//                                ListenerInvocationErrorHandler.class,
//                                LoggingErrorHandler::new)),
//                                conf.eventBus(),
//                        conf.getComponent(TokenStore.class, InMemoryTokenStore::new),
//                        conf.getComponent(TransactionManager.class, NoTransactionManager::instance),
//                        conf.messageMonitor(EventProcessor.class, name)));

        return configurer
                .configureEmbeddedEventStore(config -> new InMemoryEventStorageEngine())
                .registerComponent(Haushaltsbuchverzeichnis.class, config -> haushaltsbuchverzeichnis)
                .configureMessageMonitor((config) -> (componentType, componentName) -> monitor)
                .registerModule(indexer);
    }
}
