package com.github.haschi.haushaltsbuch.infrastruktur;

import com.github.haschi.haushaltsbuch.rest.ApiInfo;
import com.strategicgains.hyperexpress.HyperExpress;
import com.strategicgains.hyperexpress.domain.hal.HalResourceFactory;
import org.jboss.logging.Logger;

import javax.annotation.PostConstruct;
import javax.ejb.Singleton;
import javax.ejb.Startup;

import static com.strategicgains.hyperexpress.RelTypes.SELF;

@Singleton
@Startup
public class HalKonfiguration
{
    private static final Logger log = Logger.getLogger(HalKonfiguration.class.getName());

    @PostConstruct
    private void startup()
    {
        if (log.isInfoEnabled())
        {
            log.info("HAL Konfiguration erzeugen");
        }

        final HalResourceFactory halResourceFactory = new ImmutablesHalResourceFactory();

        HyperExpress.registerResourceFactoryStrategy(
                halResourceFactory,
                "application/hal+json");

        HyperExpress.registerResourceFactoryStrategy(
                halResourceFactory,
                "application/json");

        HyperExpress.relationships()
                .forClass(ApiInfo.class)
                .rel(SELF, "/");
    }
}
