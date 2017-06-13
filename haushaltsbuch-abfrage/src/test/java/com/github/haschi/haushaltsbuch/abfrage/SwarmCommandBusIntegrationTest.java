package com.github.haschi.haushaltsbuch.abfrage;

import com.github.haschi.haushaltsbuch.api.ImmutableBeginneHaushaltsbuchführung;
import org.axonframework.config.Configuration;
import org.axonframework.config.DefaultConfigurer;
import org.axonframework.jgroups.commandhandling.JGroupsConnector;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.wildfly.swarm.Swarm;

import java.util.UUID;

public class SwarmCommandBusIntegrationTest
{
    private Swarm swarm;
    private Configuration configuration;
    private JGroupsConnector connector;

    @Before
    public void start() throws Exception
    {
        swarm = Main.createSwarm();
        swarm.start();
        swarm.deploy();

        final AxonKonfiguration axon = new AxonKonfiguration();
        connector = axon.createConnector();

        configuration = DefaultConfigurer.defaultConfiguration()
                .configureCommandBus(configuration ->
                                     {
                                         return axon.setupDistributedCommandBus(connector);
                                     })
                // .registerCommandHandler(c -> new TestCommandHandler())
                .buildConfiguration();


        configuration.start();
        connector.connect();
    }

    @After
    public void stop() throws Exception
    {
        swarm.stop();
        connector.disconnect();
        configuration.shutdown();
    }

    @Test
    public void commands_werden_im_swarm_ausgeführt() {

        //assertThatThrownBy(() ->
        configuration.commandGateway()
                .send(ImmutableBeginneHaushaltsbuchführung.builder()
                              .id(UUID.randomUUID())
                .build());
        //.isInstanceOf(CommandDispatchException.class);
    }
}
