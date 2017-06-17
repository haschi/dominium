package com.github.haschi.haushaltsbuch.abfrage;

import org.axonframework.commandhandling.CommandBus;
import org.axonframework.commandhandling.SimpleCommandBus;
import org.axonframework.commandhandling.distributed.AnnotationRoutingStrategy;
import org.axonframework.commandhandling.distributed.DistributedCommandBus;
import org.axonframework.config.Configuration;
import org.axonframework.config.ModuleConfiguration;
import org.axonframework.jgroups.commandhandling.JGroupsConnector;
import org.axonframework.serialization.xml.XStreamSerializer;
import org.jgroups.JChannel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CommandBusKonfiguration implements ModuleConfiguration
{
    Logger log = LoggerFactory.getLogger(CommandBusKonfiguration.class);

    private JChannel channel;
    private JGroupsConnector connector;

    public JChannel createChannel() {
        return new JChannel(true);
    }

    public JGroupsConnector createConnector(JChannel channel)
    {
        CommandBus localCommandBus = new SimpleCommandBus();
        return new JGroupsConnector(
                localCommandBus,
                channel,
                "haushaltsbuch-cluster",
                new XStreamSerializer(),
                new AnnotationRoutingStrategy());
    }

    public CommandBus setupDistributedCommandBus(final Configuration configuration)
    {
        final CommandBus commandBus = new DistributedCommandBus(connector, connector);

        // final CommandBus commandBus =  new SimpleCommandBus();

        //        configuration.onStart(this::startCommandBusConnection);
        //        configuration.onShutdown(this::stopCommandBusConnection);

        //        configuration.onStart(() -> {
        //            // final Logger log = LoggerFactory.getLogger("DistributedCommandBus Start");
        //            log.info("Verbindung zum verteilten Commandbus wird aufgebaut");
        //        });

        //        configuration.onStart(() -> {
        //            try
        //            {
        //                final Logger log = LoggerFactory.getLogger("DistributedCommandBus Start");
        //                log.info("Verbindung zum verteilten Commandbus wird hergestellt");
        ////                channel.connect("haushaltsbuch");
        ////                connector.connect();
        //            } catch (Exception e)
        //            {
        //                log.error("Verbindungsaufbau fehlgeschlagen", e);
        //            }
        //        });
        return commandBus;
    }

    public void startCommandBusConnection()
    {
        try
        {
            // channel.connect("haushaltsbuch");
            connector.connect();
        } catch (final Exception e) {
            log.error("Verbindung kann nicht hergestellt werden", e);
        }
    }

    public void stopCommandBusConnection() {
        connector.disconnect();
        channel.disconnect();
        channel.close();
    }

    public void initialize() {
        this.channel = createChannel();
        this.connector = createConnector(this.channel);
    }

    @Override
    public void initialize(Configuration config)
    {
        initialize();
    }

    public void start() {
        startCommandBusConnection();
    }

    @Override
    public void shutdown()
    {
        stop();
    }

    public void stop() {
        stopCommandBusConnection();
    }
}
