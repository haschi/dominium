package com.github.haschi.cqrs;

import com.github.haschi.haushaltsbuch.abfrage.Systemumgebung;
import com.github.haschi.haushaltsbuch.api.ImmutableBeginneHaushaltsbuchführung;
import org.axonframework.commandhandling.distributed.DistributedCommandBus;
import org.axonframework.config.Configuration;
import org.axonframework.config.Configurer;
import org.axonframework.config.DefaultConfigurer;
import org.axonframework.jgroups.commandhandling.JGroupsConnector;
import org.junit.Test;

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

public class CqrsKonfiguratorTest
{
    @Test
    public void konfigurieren() {
        Systemumgebung umgebung = new Integrationsumgebung();

        final Configurer configurer = DefaultConfigurer.defaultConfiguration()
                .configureCommandBus(umgebung::erzeugeCommandBus)
                .registerComponent(JGroupsConnector.class, umgebung::erzeugeConnector)
                .configureEmbeddedEventStore(umgebung::erzeugeStorageEngine);

        final Configuration configuration = configurer.buildConfiguration();
        configuration.start();
        assertThat(configuration.commandBus()).isInstanceOf(DistributedCommandBus.class);
        assertThat(configuration.commandGateway()).isNotNull();

        configuration.commandGateway().send(ImmutableBeginneHaushaltsbuchführung.builder()
            .id(UUID.randomUUID())
            .build());

        configuration.shutdown();
    }
}
