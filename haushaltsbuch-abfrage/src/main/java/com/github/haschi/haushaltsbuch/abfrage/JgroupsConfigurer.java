package com.github.haschi.haushaltsbuch.abfrage;

import org.axonframework.commandhandling.SimpleCommandBus;
import org.axonframework.commandhandling.distributed.AnnotationRoutingStrategy;
import org.axonframework.commandhandling.distributed.DistributedCommandBus;
import org.axonframework.config.Configuration;
import org.axonframework.config.Configurer;
import org.axonframework.config.ModuleConfiguration;
import org.axonframework.jgroups.commandhandling.JGroupsConnector;
import org.axonframework.serialization.xml.XStreamSerializer;
import org.jgroups.JChannel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class JgroupsConfigurer
{
    Logger log = LoggerFactory.getLogger(JgroupsConfigurer.class);

    private Configurer configurer;
    private JChannel channel;
    private JGroupsConnector connector;

    public JgroupsConfigurer(Configurer configurer)
    {

        this.configurer = configurer;
    }

    public Configurer jgroupsConfiguration(String clusterName) throws Exception
    {

        channel = new JChannel("udp.xml");

        connector = new JGroupsConnector(
                new SimpleCommandBus(),
                channel,
                clusterName,
                new XStreamSerializer(),
                new AnnotationRoutingStrategy());

        try
        {
            connector.connect();
        } catch (Exception e)
        {
            log.error("Verbindungsfehler", e);
        }

        return configurer
                .configureCommandBus(config -> new DistributedCommandBus(
                        config.getComponent(JGroupsConnector.class),
                        config.getComponent(JGroupsConnector.class)))
                .registerComponent(JGroupsConnector.class, config -> connector)
                .registerComponent(JChannel.class, config -> channel)
                .registerModule(new ModuleConfiguration()
                {
                    private Configuration config;
                    Logger log = LoggerFactory.getLogger(ModuleConfiguration.class);

                    @Override
                    public void initialize(Configuration config)
                    {
                        this.config = config;
                    }

                    @Override
                    public void start()
                    {
                        assert config.commandBus() != null;

                        try
                        {
                            config.getComponent(JGroupsConnector.class).connect();
                        } catch (Exception e)
                        {
                            log.error("Verbindungsfehler", e);
                        }
                    }

                    @Override
                    public void shutdown()
                    {
                        log.info("Verbindung beenden");

                        config.getComponent(JGroupsConnector.class).disconnect();
                        config.getComponent(JChannel.class).disconnect();
                        config.getComponent(JChannel.class).close();
                    }
                });
    }
}
