package com.github.haschi.haushaltsbuch.abfrage.infrastruktur;

import com.github.haschi.haushaltsbuch.abfrage.domäne.DomainAutomationApi;
import com.github.haschi.haushaltsbuch.abfrage.rest.RestAutomationApi;
import cucumber.runtime.java.picocontainer.PicoFactory;

public class CustomPicoFactory extends PicoFactory
{
    public CustomPicoFactory()
    {
        final String testebene = System.getProperty("com.github.haschi.testebene");
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
