package com.github.haschi.dominium.testdomaene;

import com.github.haschi.dominium.infrastructure.KonkurrierenderZugriff;
import com.github.haschi.dominium.testdomaene.generiert.TestAggregatProxy;
import com.github.haschi.dominium.testdomaene.generiert.TestAggregatRepository;

public class ZustandÄndernService {

    private final TestAggregatRepository repository;

    public ZustandÄndernService(final TestAggregatRepository repository) {
        super();

        this.repository = repository;
    }

    public final void ausführen(final ÄndereZustand command) throws KonkurrierenderZugriff {
        final TestAggregatProxy aggregat = this.repository.getById(command.testAggregateId());
        aggregat.zustandÄndern(command);
        this.repository.save(aggregat);
    }
}
