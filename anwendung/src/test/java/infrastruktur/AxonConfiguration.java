package infrastruktur;

import it.kamaladafrica.cdi.axonframework.AutoConfigure;
import org.apache.deltaspike.core.api.exclude.Exclude;
import org.apache.deltaspike.core.api.projectstage.ProjectStage;
import org.axonframework.commandhandling.CommandBus;
import org.axonframework.commandhandling.SimpleCommandBus;
import org.axonframework.commandhandling.gateway.CommandGateway;
import org.axonframework.commandhandling.gateway.DefaultCommandGateway;
import org.axonframework.eventhandling.EventBus;
import org.axonframework.eventhandling.scheduling.EventScheduler;
import org.axonframework.eventhandling.scheduling.java.SimpleEventScheduler;
import org.axonframework.eventsourcing.AggregateSnapshotter;
import org.axonframework.eventsourcing.EventCountSnapshotterTrigger;
import org.axonframework.eventsourcing.Snapshotter;
import org.axonframework.eventsourcing.SnapshotterTrigger;
import org.axonframework.eventstore.EventStore;
import org.axonframework.eventstore.supporting.VolatileEventStore;
import org.axonframework.saga.SagaRepository;
import org.axonframework.saga.repository.inmemory.InMemorySagaRepository;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.Produces;
import java.io.File;
import java.util.concurrent.Executors;

@ApplicationScoped
@Exclude(ifProjectStage = ProjectStage.Production.class)
public final class AxonConfiguration
{
    private static final File storageDir = new File("/home/matthias/storage");

    @Produces
    @AutoConfigure
    @ApplicationScoped
    public EventStore eventStore()
    {

        return new VolatileEventStore();
        // return new FileSystemEventStore(new SimpleEventFileResolver(storageDir));
    }

    @Produces
    @ApplicationScoped
    public EventScheduler eventScheduler(final EventBus eventBus)
    {
        return new SimpleEventScheduler(Executors.newSingleThreadScheduledExecutor(), eventBus);
    }

    @Produces
    @AutoConfigure
    @ApplicationScoped
    public SagaRepository sagaRepository()
    {
        return new InMemorySagaRepository();
    }

    @Produces
    @AutoConfigure
    @ApplicationScoped
    public CommandBus commandBus()
    {
        //final MyUnitIfWorkFactory myUnitIfWorkFactory = new MyUnitIfWorkFactory();
        final SimpleCommandBus simpleCommandBus = new SimpleCommandBus();
        // simpleCommandBus.setTransactionManager(new JeeTransactionManager());
        //simpleCommandBus.setUnitOfWorkFactory(myUnitIfWorkFactory);
        return simpleCommandBus;
    }

    @Produces
    @ApplicationScoped
    public CommandGateway commandGateway(final CommandBus commandBus)
    {
        return new DefaultCommandGateway(commandBus);
    }

    // Snapshots

    @Produces
    @AutoConfigure
    @ApplicationScoped
    public Snapshotter snapshotter()
    {
        return new AggregateSnapshotter();
    }

    @Produces
    @ApplicationScoped
    public SnapshotterTrigger snapshotterTrigger(final Snapshotter snapshotter)
    {
        final EventCountSnapshotterTrigger trigger = new EventCountSnapshotterTrigger();
        trigger.setSnapshotter(snapshotter);
        return trigger;
    }

}
