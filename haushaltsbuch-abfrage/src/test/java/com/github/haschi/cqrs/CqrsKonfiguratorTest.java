package com.github.haschi.cqrs;

import org.axonframework.commandhandling.distributed.DistributedCommandBus;
import org.axonframework.config.Configuration;
import org.axonframework.config.DefaultConfigurer;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

public class CqrsKonfiguratorTest
{
    private boolean commandHandlerAufgerufen;
    private Configuration clientconfiguration;
    private Configuration serverConfiguration;
    private TestCommandHandler commandHandler = new TestCommandHandler();

    @Before
    public void umgebungenStarten() throws InterruptedException
    {
        commandHandler = new TestCommandHandler();
        serverKonfigurieren();
        clientKonfigurieren();
    }

    @Test
    public void konfigurieren()
    {
        clientconfiguration.commandGateway().sendAndWait(new TestCommand(UUID.randomUUID()));
        assertThat(commandHandler.aufgerufen()).isTrue();
    }

    private void serverKonfigurieren() throws InterruptedException
    {
        final Integrationsumgebung serverumgebung = new Integrationsumgebung();

        serverConfiguration = DefaultConfigurer.defaultConfiguration()
                .registerModule(serverumgebung)
                .configureCommandBus(serverumgebung::erzeugeCommandBus)
                .configureEmbeddedEventStore(serverumgebung::erzeugeStorageEngine)
                .registerCommandHandler(c -> commandHandler)
                .buildConfiguration();

        serverConfiguration.start();
        // Aufwärmen.
        assertThat(serverConfiguration.commandBus()).isInstanceOf(DistributedCommandBus.class);
        Thread.sleep(1000);
    }

    private void clientKonfigurieren() throws InterruptedException
    {
        Integrationsumgebung clientumgebung = new Integrationsumgebung();

        clientconfiguration = DefaultConfigurer.defaultConfiguration()
                .configureCommandBus(clientumgebung::erzeugeCommandBus)
                .configureEmbeddedEventStore(clientumgebung::erzeugeStorageEngine)
                .registerModule(clientumgebung)
                .buildConfiguration();

        // Reihenfolge:
        // 1. Starter wird ausgeführt.
        // 2. Komponenten (CommandBus ist auch eine Komponente) werden erzeugt
        clientconfiguration.start();

        Thread.sleep(1000);
        // Aufwärmen!
        assertThat(clientconfiguration.commandBus()).isInstanceOf(DistributedCommandBus.class);
        assertThat(clientconfiguration.commandGateway()).isNotNull();
    }

    @After
    public void testumgebungHerunterfahren() {
        clientconfiguration.shutdown();
        serverConfiguration.shutdown();
    }

}
