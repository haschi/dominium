package com.github.haschi.cqrs;

import org.junit.After;
import org.junit.Test;

import java.util.UUID;

public class WennEineAnweisungÜberDenVerteiltenKommandobusGesendetWirdTest
{
    private
    VerteilterBefehlsbusSteps steps = new VerteilterBefehlsbusSteps();

    @After
    public void umgebungHerunterfahren() throws Exception
    {
        steps.close();
    }

    @Test
    public void server_kann_befehl_ausführen() throws Exception
    {
        final TestCommand befehl = new TestCommand(UUID.randomUUID());

        steps.ich_habe_einen_server_mit_verteilten_befehlsbus_der_gruppe_hinzugefügt("haushaltsbuch");
        steps.ich_habe_einen_client_mit_verteilten_befehlsbus_der_gruppe_hinzugefügt("haushaltsbuch");

        steps.ich_versende_einen_befehl_am_server(befehl);

        steps.werde_ich_den_ereignishandler_aufgerufen_haben();
    }

    @Test
    public void client_kann_kommando_ausführen() throws Exception
    {
        final TestCommand befehl = new TestCommand(UUID.randomUUID());

        steps.ich_habe_einen_server_mit_verteilten_befehlsbus_der_gruppe_hinzugefügt("haushaltsbuch");
        steps.ich_habe_einen_client_mit_verteilten_befehlsbus_der_gruppe_hinzugefügt("haushaltsbuch");

        steps.ich_versende_einen_befehl_am_client(befehl);

        steps.werde_ich_den_ereignishandler_aufgerufen_haben();
    }
}
