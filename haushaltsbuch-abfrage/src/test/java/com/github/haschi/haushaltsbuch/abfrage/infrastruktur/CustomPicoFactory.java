package com.github.haschi.haushaltsbuch.abfrage.infrastruktur;

import com.github.haschi.haushaltsbuch.abfrage.domäne.DomainAutomationApi;
import com.github.haschi.haushaltsbuch.abfrage.rest.RestAutomationApi;
import cucumber.runtime.java.picocontainer.PicoFactory;

public class CustomPicoFactory extends PicoFactory
{
    // Logger log = LoggerFactory.getLogger(CustomPicoFactory.class);

    public CustomPicoFactory()
    {
        final String testebene = System.getProperty("com.github.haschi.testebene");
        if("domäne".equals(testebene))
        {
            // log.info(MessageFormat.format("Testebene {0}", testebene));
            addClass(DomainAutomationApi.class);
        }
        else
        {
            // log.info(MessageFormat.format("Testebene {0}", testebene));
            addClass(RestAutomationApi.class);
        }
    }
}
