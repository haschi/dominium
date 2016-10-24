package infrastruktur;

import it.kamaladafrica.cdi.axonframework.AutoConfigure;
import org.axonframework.eventhandling.EventBus;
import org.axonframework.eventhandling.SimpleEventBus;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.Produces;

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
