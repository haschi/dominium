package com.github.haschi.dominium.modell

import com.github.haschi.dominium.testdomaene.TestAggregat
import com.github.haschi.dominium.testdomaene.generiert.TestAggregatEventStore
import com.github.haschi.dominium.testdomaene.generiert.TestAggregatProxy
import com.github.haschi.dominium.testdomaene.generiert.TestAggregatRepository
import spock.lang.Shared
import spock.lang.Specification


class AggregateWerdenInIhremRepositoryAufbewahrtTest extends Specification {

    @Shared identitätsmerkmal = UUID.randomUUID()

    TestAggregatEventStore storage = new TestAggregatEventStore();

    def "Aus dem Repository geladene Aggregate besitzen keine Änderungen"() {
        given: "ich habe ein Repository angelegt"
        final TestAggregatRepository repository = new TestAggregatRepository(storage)
        final TestAggregatProxy aggregat = new TestAggregatProxy(identitätsmerkmal, Version.NEU)

        aggregat.zustandÄndern(42L)
        repository.save(aggregat)

        when: "ich ein Aggregat aus dem Repository hole"
        TestAggregatProxy restored = repository.getById(identitätsmerkmal)

        then: "wird das Aggregat keine Änderungen besitzen"
        TestAggregat.Snapshot.from(aggregat) == TestAggregat.Snapshot.from(restored)
        restored.uncommitedChanges == []
    }
}