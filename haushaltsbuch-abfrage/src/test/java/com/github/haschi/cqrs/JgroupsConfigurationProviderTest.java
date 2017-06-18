package com.github.haschi.cqrs;

import org.axonframework.config.Configuration;
import org.axonframework.config.DefaultConfigurer;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

public class JgroupsConfigurationProviderTest
{
    Configuration client;
    Configuration server;
    TestCommandHandler handler;

    @Before
    public void umgebungStarten() throws Exception
    {
        handler = new TestCommandHandler();


        server = new JgroupsConfigurer(DefaultConfigurer.defaultConfiguration())
                .jgroupsConfiguration()
                .registerCommandHandler(config -> handler)
                .buildConfiguration();

//        server = JgroupsConfigurer.jgroupsConfiguration()
//                .registerCommandHandler(config -> handler)
//                .buildConfiguration();

        server.start();

        client = new JgroupsConfigurer(DefaultConfigurer.defaultConfiguration())
                .jgroupsConfiguration()
                .buildConfiguration();

        client.start();
    }

    @After
    public void umgebungHerunterfahren() {
        client.shutdown();
        server.shutdown();
        handler = null;
    }

    @Test
    public void server_kann_kommando_ausführen() {

        server.commandGateway().sendAndWait(new TestCommand(UUID.randomUUID()));
        assertThat(handler.aufgerufen()).isTrue();

        client.commandGateway().sendAndWait(new TestCommand(UUID.randomUUID()));
        assertThat(handler.aufgerufen()).isTrue();
    }

    @Test
    public void client_kann_kommando_ausführen() {
        client.commandGateway().sendAndWait(new TestCommand(UUID.randomUUID()));
        assertThat(handler.aufgerufen()).isTrue();
    }
}
