package com.github.haschi.haushaltsbuch.abfrage.infrastruktur;

import com.github.haschi.haushaltsbuch.abfrage.Anwendungskonfiguration;
import com.github.haschi.haushaltsbuch.abfrage.domäne.DomainAutomationApi;
import com.github.haschi.haushaltsbuch.abfrage.domäne.Synchonisierungsmonitor;
import com.github.haschi.haushaltsbuch.abfrage.domäne.Testumgebung;
import com.github.haschi.haushaltsbuch.abfrage.rest.RestAutomationApi;
import cucumber.runtime.java.picocontainer.PicoFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.text.MessageFormat;

public class CustomPicoFactory extends PicoFactory
{
    private static Logger log = LoggerFactory.getLogger(CustomPicoFactory.class);

    public CustomPicoFactory()
    {
        final String testebene = System.getProperty("com.github.haschi.testebene");
        log.info(MessageFormat.format("Testebene {0}", testebene));

        addClass(Anwendungskonfiguration.class);

        if("domäne".equals(testebene))
        {
            addClass(Synchonisierungsmonitor.class);
            addClass(Testumgebung.class);
            addClass(DomainAutomationApi.class);
        }
        else
        {
            addClass(RestAutomationApi.class);
        }
    }
}
