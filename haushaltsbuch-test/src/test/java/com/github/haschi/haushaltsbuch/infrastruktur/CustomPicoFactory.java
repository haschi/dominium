package com.github.haschi.haushaltsbuch.infrastruktur;

import com.github.haschi.haushaltsbuch.domäne.Warteschlangenmonitor;
import com.github.haschi.haushaltsbuch.rest.Anwendungskonfiguration;
import com.github.haschi.haushaltsbuch.rest.AutomationApi;
import com.github.haschi.haushaltsbuch.rest.Synchronisierungsmonitor;
import com.github.haschi.haushaltsbuch.rest.Testumgebung;
import cucumber.runtime.java.picocontainer.PicoFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.text.MessageFormat;

public final class CustomPicoFactory extends PicoFactory
{
    private static final Logger log = LoggerFactory.getLogger(CustomPicoFactory.class);

    public CustomPicoFactory()
    {
        final String testebene = System.getProperty("com.github.haschi.testebene");
        log.info(MessageFormat.format("Testebene {0}", testebene));

        if (testebene == null)
        {
            throw new IllegalStateException("Undefinierte Testebene");
        }

        addClass(TestEventHandler.class);

        if("domäne".equals(testebene))
        {
            addClass(Warteschlangenmonitor.class);
            addClass(com.github.haschi.haushaltsbuch.domäne.Anwendungskonfiguration.class);
            addClass(com.github.haschi.haushaltsbuch.domäne.Testumgebung.class);
            addClass(com.github.haschi.haushaltsbuch.domäne.AutomationApi.class);
        }

        if ("fixture".equals(testebene))
        {
            addClass(com.github.haschi.haushaltsbuch.fixture.AutomationApi.class);
        }

        if ("api".equals(testebene))
        {
            addClass(Synchronisierungsmonitor.class);
            addClass(AutomationApi.class);
            addClass(Anwendungskonfiguration.class);
            addClass(Testumgebung.class);
        }
    }
}
