package com.github.haschi.haushaltsbuch.abfrage;

import com.github.haschi.haushaltsbuch.api.ImmutableBeginneHaushaltsbuchführung;
import org.axonframework.config.Configuration;
import org.axonframework.config.DefaultConfigurer;
import org.axonframework.jgroups.commandhandling.JGroupsConnector;
import org.jgroups.JChannel;
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
    private JChannel channel;

    @Before
    public void start() throws Exception
    {
        swarm = Main.createSwarm();
        swarm.start();
        swarm.deploy();

        Thread.sleep(2000);
        final CqrsKonfigurator axon = new CqrsKonfigurator();
        channel = axon.createChannel();
        connector = axon.createConnector(channel);

        configuration = DefaultConfigurer.defaultConfiguration()
                .configureCommandBus(configuration ->
                                     {
                                         return axon.setupDistributedCommandBus(connector);
                                     })
                // .registerCommandHandler(c -> new TestCommandHandler())
                .buildConfiguration();


        connector.connect();
        configuration.start();
    }

    @After
    public void stop() throws Exception
    {
        assert swarm != null : "Wildfly Swarm nicht gestartet";
        swarm.stop();

        configuration.shutdown();
        connector.disconnect();
        channel.close();
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
