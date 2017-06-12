//package com.github.haschi.haushaltsbuch.abfrage;
//
//import org.axonframework.config.Configuration;
//import org.axonframework.eventsourcing.GenericDomainEventMessage;
//import org.axonframework.eventsourcing.eventstore.EventStorageEngine;
//import org.wildfly.swarm.Swarm;
//
//import javax.inject.Inject;
//import javax.inject.Singleton;
//
//@Singleton
//public class CucumberHooks
//{
//    private Configuration configuration;
//    private long sequenceNumber;
//    @Inject
//    private Configuration konfiguration;
//
//    @Inject
//    private EventStorageEngine engine;
//
//    private Swarm swarm;
//
//    public <T> void haushaltsführungBegonnen(
//            final AggregateProxy<T> aggregat,
//            final Object payload)
//    {
//        engine
//                .appendEvents(new GenericDomainEventMessage<Object>(
//                aggregat.getType(),
//                aggregat.getIdentifier().toString(),
//                sequenceNumber++,
//                payload)
//        );
//    }
//
//    // @Before
//    public void cqrsStarten() throws Exception
//    {
//            final Swarm swarm = Main.createSwarm();
//            swarm.start();
//            swarm.deploy();
//    }
//
//    // @After
//    public void stop() throws Exception
//    {
//        swarm.stop();
//        swarm = null;
//    }
//}
