package com.github.haschi.haushaltsbuch.abfrage;

import org.axonframework.commandhandling.CommandBus;
import org.axonframework.commandhandling.SimpleCommandBus;
import org.axonframework.commandhandling.distributed.AnnotationRoutingStrategy;
import org.axonframework.commandhandling.distributed.DistributedCommandBus;
import org.axonframework.common.Registration;
import org.axonframework.config.Component;
import org.axonframework.config.Configuration;
import org.axonframework.config.ModuleConfiguration;
import org.axonframework.jgroups.commandhandling.JGroupsConnector;
import org.axonframework.serialization.xml.XStreamSerializer;
import org.jgroups.JChannel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

public class CommandBusKonfigurierer implements ModuleConfiguration
{
    List<Registration> registrations = new ArrayList<>();

    private Logger log = LoggerFactory.getLogger(CommandBusKonfigurierer.class);

    private JChannel channel;
    private JGroupsConnector connector;
    private Configuration parent;
    private Component<DistributedCommandBus> simpleCommandBusComponent;

    private JChannel createChannel()
    {
        return new JChannel(true);
    }

    private JGroupsConnector createConnector(JChannel channel)
    {
        CommandBus localCommandBus = new SimpleCommandBus();
        return new JGroupsConnector(
                localCommandBus,
                channel,
                "haushaltsbuch-cluster",
                new XStreamSerializer(),
                new AnnotationRoutingStrategy());
    }

    public CommandBus setupDistributedCommandBus()
    {
        return new DistributedCommandBus(connector, connector);
    }

    public void initialize()
    {
        this.channel = createChannel();
        this.connector = createConnector(this.channel);
    }

    @Override
    public void initialize(Configuration config)
    {
        this.parent = config;

        initialize();
        simpleCommandBusComponent = new Component<>(
                () -> parent,
                CommandBus.class.getSimpleName(),
                c -> new DistributedCommandBus(connector, connector));
    }

    public void start()
    {
        try
        {
            // channel.connect("haushaltsbuch");
            connector.connect();
        } catch (final Exception e)
        {
            log.error("Verbindung kann nicht hergestellt werden", e);
        }
    }

    @Override
    public void shutdown()
    {
        connector.disconnect();
        channel.disconnect();
        channel.close();
    }
}
