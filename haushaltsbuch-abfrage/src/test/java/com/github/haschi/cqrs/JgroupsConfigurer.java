package com.github.haschi.cqrs;

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
    public static Configurer jgroupsConfiguration(Configurer configurer)
    {
        Logger log = LoggerFactory.getLogger(JgroupsConfigurer.class);

        final JChannel channel = new JChannel(true);

        final JGroupsConnector connector = new JGroupsConnector(
                new SimpleCommandBus(),
                channel,
                "haushaltsbuch",
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

                        try {
                            // config.getComponent(JGroupsConnector.class).connect();
                        } catch (Exception e) {
                            log.error("Verbindungsfehler", e);
                        }
                    }

                    @Override
                    public void shutdown()
                    {
                        log.info("Verbindung beenden");
                        connector.disconnect();
                        channel.disconnect();
                        channel.close();


                    }
                });
    }
}
