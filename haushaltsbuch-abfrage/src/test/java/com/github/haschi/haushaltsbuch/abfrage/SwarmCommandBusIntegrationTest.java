package com.github.haschi.haushaltsbuch.abfrage;

import com.github.haschi.cqrs.Integrationsumgebung;
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
    private JChannel channel;
    private JGroupsConnector connector;

    @Before
    public void start() throws Exception
    {
        swarm = Main.createSwarm();
        swarm.start();
        swarm.deploy();

        CommandBusKonfigurierer cbk = new CommandBusKonfigurierer();
        cbk.initialize();
        final Systemumgebung integrationstestumgebung = new Integrationsumgebung();
        final CqrsKonfigurator axon = new CqrsKonfigurator(integrationstestumgebung);

        configuration = DefaultConfigurer.defaultConfiguration()
                .configureCommandBus(integrationstestumgebung::erzeugeCommandBus)
                .registerModule(cbk)
                .buildConfiguration();

        configuration.start();
    }

    @After
    public void stop() throws Exception
    {
        assert swarm != null : "Wildfly Swarm nicht gestartet";
        swarm.stop();


        configuration.shutdown();
    }

    @Test
    public void commands_werden_im_swarm_ausgeführt() {

        configuration.commandGateway()
                .send(ImmutableBeginneHaushaltsbuchführung.builder()
                              .id(UUID.randomUUID())
                .build());
    }
}
