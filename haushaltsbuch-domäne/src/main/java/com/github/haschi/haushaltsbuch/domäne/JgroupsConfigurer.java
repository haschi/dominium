package com.github.haschi.haushaltsbuch.domäne;

import org.axonframework.commandhandling.SimpleCommandBus;
import org.axonframework.commandhandling.distributed.AnnotationRoutingStrategy;
import org.axonframework.commandhandling.distributed.DistributedCommandBus;
import org.axonframework.config.Configuration;
import org.axonframework.config.Configurer;
import org.axonframework.config.ModuleConfiguration;
import org.axonframework.jgroups.commandhandling.JGroupsConnector;
import org.axonframework.serialization.xml.XStreamSerializer;
import org.jgroups.JChannel;

public class JgroupsConfigurer
{
    private final Configurer configurer;
    private JChannel channel;
    private JGroupsConnector connector;

    public JgroupsConfigurer(final Configurer configurer)
    {

        this.configurer = configurer;
    }

    public Configurer jgroupsConfiguration(final String clusterName) throws Exception, KonfigurationFehlgeschlagen
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
        } catch (final Exception e)
        {
            throw new KonfigurationFehlgeschlagen(e);
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

                    @Override
                    public void initialize(final Configuration config)
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
                        } catch (final Exception e)
                        {
                            throw new KonfigurationFehlgeschlagen(e);
                        }
                    }

                    @Override
                    public void shutdown()
                    {
                        config.getComponent(JGroupsConnector.class).disconnect();
                        config.getComponent(JChannel.class).disconnect();
                        config.getComponent(JChannel.class).close();
                    }
                });
    }
}
