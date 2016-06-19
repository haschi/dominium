package com.github.haschi.dominium.infrastructure.commandbus;

import com.github.haschi.dominium.infrastructure.KonkurrierenderZugriff;
import com.github.haschi.dominium.testdomaene.generiert.TestAggregatProxy;
import com.github.haschi.dominium.testdomaene.generiert.TestAggregatRepository;
import com.github.haschi.dominium.testdomaene.ÄndereZustand;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.event.Observes;
import javax.inject.Inject;

@ApplicationScoped
public class TestCommandHandler {

    @Inject
    private TestAggregatRepository repository;

    private long zustand;

    public void handle(@Observes final ÄndereZustand command) throws KonkurrierenderZugriff {

        final TestAggregatProxy aggregat = this.repository.getById(command.testAggregateId());
        aggregat.zustandÄndern(command);
        this.repository.save(aggregat);

        this.zustand = command.payload();
    }

    public long getZustand() {
        return this.zustand;
    }
}
