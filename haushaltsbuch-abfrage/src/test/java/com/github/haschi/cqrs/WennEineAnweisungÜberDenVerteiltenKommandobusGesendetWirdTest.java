package com.github.haschi.cqrs;

import net.serenitybdd.junit.runners.SerenityRunner;
import net.thucydides.core.annotations.Narrative;
import net.thucydides.core.annotations.Steps;
import net.thucydides.core.annotations.Title;
import org.junit.After;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.UUID;

@RunWith(SerenityRunner.class)
@Narrative(text = {
        "Als Programmierer",
        "möchte ich einen Befehl vom Client an den Server schicken",
        "um einen Befehlshandler auf dem Server auszuführen"})
public class WennEineAnweisungÜberDenVerteiltenKommandobusGesendetWirdTest
{
    @Steps
    private
    VerteilterBefehlsbusSteps steps;

    @After
    public void umgebungHerunterfahren() throws Exception
    {
        steps.close();
    }

    @Test
    @Title("Server kann einen Befehl lokal ausführen")
    public void server_kann_befehl_ausführen() throws Exception
    {
        final TestCommand befehl = new TestCommand(UUID.randomUUID());

        steps.ich_habe_einen_server_mit_verteilten_befehlsbus_der_gruppe_hinzugefügt("haushaltsbuch");
        steps.ich_habe_einen_client_mit_verteilten_befehlsbus_der_gruppe_hinzugefügt("haushaltsbuch");

        steps.ich_versende_einen_befehl_am_server(befehl);

        steps.werde_ich_den_ereignishandler_aufgerufen_haben();
    }

    @Test
    @Title("Client kann einen Befehl auf dem Server ausführen")
    public void client_kann_kommando_ausführen() throws Exception
    {
        final TestCommand befehl = new TestCommand(UUID.randomUUID());

        steps.ich_habe_einen_server_mit_verteilten_befehlsbus_der_gruppe_hinzugefügt("haushaltsbuch");
        steps.ich_habe_einen_client_mit_verteilten_befehlsbus_der_gruppe_hinzugefügt("haushaltsbuch");

        steps.ich_versende_einen_befehl_am_client(befehl);

        steps.werde_ich_den_ereignishandler_aufgerufen_haben();
    }
}
