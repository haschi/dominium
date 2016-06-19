package com.github.haschi.dominium.infrastructure.commandbus;

import com.github.haschi.dominium.testdomaene.generiert.TestAggregatEventStore;
import com.github.haschi.dominium.testdomaene.generiert.TestAggregatRepository;
import javax.enterprise.inject.Produces;

public class RepositoryProducer {

    private static final TestAggregatEventStore eventStore = new TestAggregatEventStore();
    private static final TestAggregatRepository repository = new TestAggregatRepository(eventStore);

    @Produces
    public TestAggregatRepository createRepository() {
        return repository;
    }

    @Produces @Testing
    public TestAggregatEventStore createEventStore() {
        return eventStore;
    }
}
