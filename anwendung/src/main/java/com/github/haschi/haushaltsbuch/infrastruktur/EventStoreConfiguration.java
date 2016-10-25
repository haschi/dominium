package com.github.haschi.haushaltsbuch.infrastruktur;

import it.kamaladafrica.cdi.axonframework.AutoConfigure;
import org.apache.deltaspike.core.api.exclude.Exclude;
import org.apache.deltaspike.core.api.projectstage.ProjectStage;
import org.axonframework.eventhandling.EventBus;
import org.axonframework.eventhandling.SimpleEventBus;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.Produces;

@Exclude(exceptIfProjectStage = ProjectStage.Production.class)
public class EventStoreConfiguration
{
    @Produces
    @AutoConfigure
    @ApplicationScoped
    public EventBus eventBus()
    {
        return new SimpleEventBus();
    }
}
