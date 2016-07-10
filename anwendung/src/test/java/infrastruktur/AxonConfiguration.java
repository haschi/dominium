package infrastruktur;

import org.axonframework.commandhandling.CommandBus;
import org.axonframework.commandhandling.SimpleCommandBus;
import org.axonframework.commandhandling.gateway.CommandGateway;
import org.axonframework.commandhandling.gateway.DefaultCommandGateway;
import org.axonframework.eventhandling.EventBus;
import org.axonframework.eventhandling.SimpleEventBus;
import org.axonframework.eventhandling.scheduling.EventScheduler;
import org.axonframework.eventhandling.scheduling.java.SimpleEventScheduler;
import org.axonframework.eventsourcing.AggregateSnapshotter;
import org.axonframework.eventsourcing.EventCountSnapshotterTrigger;
import org.axonframework.eventsourcing.Snapshotter;
import org.axonframework.eventsourcing.SnapshotterTrigger;
import org.axonframework.eventstore.SnapshotEventStore;
import org.axonframework.eventstore.fs.FileSystemEventStore;
import org.axonframework.eventstore.fs.SimpleEventFileResolver;
import org.axonframework.integration.cdi.AutoConfigure;
import org.axonframework.saga.SagaRepository;
import org.axonframework.saga.repository.inmemory.InMemorySagaRepository;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.Produces;
import java.io.File;
import java.util.concurrent.Executors;

@ApplicationScoped
public final class AxonConfiguration {
    private static final File storageDir = new File("/home/matthias/storage");

    @Produces
    @AutoConfigure
    @ApplicationScoped
    public EventBus eventBus() {
        return new SimpleEventBus();
    }

    @Produces
    @AutoConfigure
    @ApplicationScoped
    public SnapshotEventStore eventStore() {
        System.out.println("Storage path: " + storageDir.getAbsolutePath());
        return new FileSystemEventStore(new SimpleEventFileResolver(storageDir));
    }

    @Produces
    @ApplicationScoped
    public EventScheduler eventScheduler(final EventBus eventBus) {
        return new SimpleEventScheduler(Executors.newSingleThreadScheduledExecutor(), eventBus);
    }

    @Produces
    @AutoConfigure
    @ApplicationScoped
    public SagaRepository sagaRepository() {
        return new InMemorySagaRepository();
    }

    @Produces
    @AutoConfigure
    @ApplicationScoped
    public CommandBus commandBus() {
        return new SimpleCommandBus();
    }

    @Produces
    @ApplicationScoped
    public CommandGateway commandGateway(final CommandBus commandBus) {
        return new DefaultCommandGateway(commandBus);
    }

    // Snapshots

    @Produces
    @AutoConfigure
    @ApplicationScoped
    public Snapshotter snapshotter() {
        return new AggregateSnapshotter();
    }

    @Produces
    @ApplicationScoped
    public SnapshotterTrigger snapshotterTrigger(final Snapshotter snapshotter) {
        final EventCountSnapshotterTrigger trigger = new EventCountSnapshotterTrigger();
        trigger.setSnapshotter(snapshotter);
        return trigger;
    }

}
