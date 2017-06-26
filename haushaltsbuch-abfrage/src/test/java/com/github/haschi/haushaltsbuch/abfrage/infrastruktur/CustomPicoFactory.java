package com.github.haschi.haushaltsbuch.abfrage.infrastruktur;

import com.github.haschi.haushaltsbuch.abfrage.domäne.DomainAutomationApi;
import com.github.haschi.haushaltsbuch.abfrage.rest.RestAutomationApi;
import cucumber.runtime.java.picocontainer.PicoFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.text.MessageFormat;

public class CustomPicoFactory extends PicoFactory
{
    Logger log = LoggerFactory.getLogger(CustomPicoFactory.class);

    public CustomPicoFactory()
    {
        final String testebene = System.getProperty("com.github.haschi.testebene");
        log.info(MessageFormat.format("Testebene {0}", testebene));

        if("domäne".equals(testebene))
        {
            addClass(DomainAutomationApi.class);
        }
        else
        {
            addClass(RestAutomationApi.class);
        }
    }
}
