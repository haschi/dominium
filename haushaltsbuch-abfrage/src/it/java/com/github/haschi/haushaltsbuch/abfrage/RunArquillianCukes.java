package com.github.haschi.haushaltsbuch.abfrage;

import org.jboss.arquillian.container.test.api.RunAsClient;
import org.junit.Test;

// @RunWith(Arquillian.class)
// @DefaultDeployment
public class RunArquillianCukes
{
    @Test
    @RunAsClient
    public void alleSzenarienAusführen() throws Throwable
    {
        String[] commandline = {

        };

        cucumber.api.cli.Main.main(commandline);
    }
}
