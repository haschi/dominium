package com.github.haschi.cqrs;

import com.github.haschi.haushaltsbuch.abfrage.JgroupsConfigurer;
import org.axonframework.commandhandling.CommandHandler;
import org.axonframework.config.Configuration;
import org.axonframework.config.DefaultConfigurer;

import static org.assertj.core.api.Assertions.assertThat;

public class VerteilterBefehlsbusSteps implements AutoCloseable
{
    private boolean aufgerufen;
    private Configuration server;
    private Configuration client;

    public void ich_habe_einen_server_mit_verteilten_befehlsbus_der_gruppe_hinzugefügt(String gruppe) throws Exception
    {
        server = new JgroupsConfigurer(DefaultConfigurer.defaultConfiguration())
                .jgroupsConfiguration(gruppe)
                .registerCommandHandler(config -> this)
                .buildConfiguration();

        server.start();
    }

    public void ich_habe_einen_client_mit_verteilten_befehlsbus_der_gruppe_hinzugefügt(String gruppe) throws Exception
    {
        client = new JgroupsConfigurer(DefaultConfigurer.defaultConfiguration())
                .jgroupsConfiguration(gruppe)
                .buildConfiguration();

        client.start();

    }

    public void ich_versende_einen_befehl_am_server(TestCommand befehl)
    {
        server.commandGateway().sendAndWait(befehl);
    }

    public void ich_versende_einen_befehl_am_client(TestCommand befehl)
    {
        client.commandGateway().sendAndWait(befehl);
    }

    public void werde_ich_den_ereignishandler_aufgerufen_haben()
    {
        assertThat(aufgerufen).isTrue();
    }

    @CommandHandler
    public void verarbeiten(TestCommand befehl) {
        this.aufgerufen = true;
    }

    @Override
    public void close() throws Exception
    {
        server.shutdown();
        client.shutdown();
    }

}
