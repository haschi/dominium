package com.github.haschi.haushaltsbuch.infrastruktur;

import com.github.haschi.haushaltsbuch.rest.ApiInfo;
import com.strategicgains.hyperexpress.HyperExpress;
import com.strategicgains.hyperexpress.domain.hal.HalResourceFactory;

import javax.annotation.PostConstruct;
import javax.ejb.Singleton;
import javax.ejb.Startup;

import static com.strategicgains.hyperexpress.RelTypes.SELF;

@Singleton
@Startup
public class HalKonfiguration
{
    @PostConstruct
    private void startup()
    {
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
