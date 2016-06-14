package com.github.haschi.dominium.modell

import com.github.haschi.dominium.persistenz.KonkurrierenderZugriff
import com.github.haschi.dominium.testdomaene.ImmutableZustandWurdeGeaendert
import com.github.haschi.dominium.testdomaene.TestAggregat
import com.github.haschi.dominium.testdomaene.generiert.ImmutableZustandWurdeGeaendertMessage
import com.github.haschi.dominium.testdomaene.generiert.TestAggregatEventStore
import com.github.haschi.dominium.testdomaene.generiert.TestAggregatProxy
import com.github.haschi.dominium.testdomaene.generiert.TestAggregatRepository
import spock.lang.Shared
import spock.lang.Specification
import spock.lang.Unroll


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

    def "Änderungen an Aggregaten, die dem Repository bekannt sind, können gespeichert werden"() {
        given: "ich habe ein Repository angelegt"
        final TestAggregatRepository repository = new TestAggregatRepository(storage)
        final TestAggregatProxy aggregat = new TestAggregatProxy(identitätsmerkmal, Version.NEU)

        aggregat.zustandÄndern(42L)
        repository.save(aggregat)
        final TestAggregatProxy restored = repository.getById(identitätsmerkmal)
        restored.zustandÄndern(43L)

        when: "ich ein Aggregat aus dem Repository hole"
        repository.save(restored)

        then: "wird das Aggregat keine Änderungen besitzen"
        storage.getEventsForAggregate(identitätsmerkmal) == [
                ImmutableZustandWurdeGeaendertMessage.of(ImmutableZustandWurdeGeaendert.of(42L)),
                ImmutableZustandWurdeGeaendertMessage.of(ImmutableZustandWurdeGeaendert.of(43L))]
        notThrown Exception
    }

    @Unroll
    def "Versionsnummer des Aggregats"() {
        final TestAggregatRepository repository = new TestAggregatRepository(storage)
        final TestAggregatProxy aggregat = new TestAggregatProxy(identitätsmerkmal, Version.NEU)

        payloads.each {payload -> aggregat.zustandÄndern(payload)}
        repository.save(aggregat)

        when: "ich ein Aggregat aus dem Repository hole"
        final TestAggregatProxy restored = repository.getById(identitätsmerkmal)

        then: "wird das Aggregat keine Änderungen besitzen"
        restored.version == Version.NEU.nachfolger(version)
        storage.getEventsForAggregate(identitätsmerkmal).size() == version

        where:
        payloads        || version
        []              || 0
        [42L]           || 1
        [42L, 43L, 44L] || 3
    }


    def "Aggregat besitzt nach dem Speichern keine ungespeicherten Änderungen mehr"() {

        given: "ich habe ein Aggregat mit nicht gespeicherten Änderungen"
        final TestAggregatRepository repository = new TestAggregatRepository(storage)
        final TestAggregatProxy aggregat = new TestAggregatProxy(identitätsmerkmal, Version.NEU)

        payloads.each {payload -> aggregat.zustandÄndern(payload)}

        when: "wenn ich das Aggregat im Repository speichere"
        repository.save(aggregat)

        then: "wird das Aggregat keine Änderungen besitzen"
        aggregat.uncommitedChanges == []

        where:
        payloads        || _
        []              || _
        [42L]           || _
        [42L, 43L, 44L] || _
    }

    def "Konkurrierenden Zugriff beim Speichern erkennen"() {
        final TestAggregatRepository repository = new TestAggregatRepository(storage)
        final TestAggregatProxy aggregat = new TestAggregatProxy(identitätsmerkmal, Version.NEU)

        payloads.each {payload -> aggregat.zustandÄndern(payload)}

        storage.saveEvents(
                identitätsmerkmal,
                [ImmutableZustandWurdeGeaendertMessage.of(ImmutableZustandWurdeGeaendert.of(42L))],
                Version.NEU)

        when: "wenn ich das Aggregat im Repository speichere"
        repository.save(aggregat)

        then: "wird das Aggregat keine Änderungen besitzen"
        thrown KonkurrierenderZugriff

        where:
        payloads        || _
        []              || _
        [42L]           || _
        [42L, 43L, 44L] || _
    }
}