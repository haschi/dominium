package com.github.haschi.haushaltsbuch.abfragen

import javaslang.collection.Stream
import org.axonframework.domain.DomainEventMessage
import org.axonframework.domain.DomainEventStream
import org.axonframework.domain.GenericDomainEventMessage
import spock.lang.Specification

class DomainEventStreamFactorySpec extends Specification {

    def "Ein leerer DomainEventStream liefert DomainEventSequence ohne Elemente"() {

        given: "Ich habe einen leeren DomainEventStream"
        DomainEventStream stream = Mock(DomainEventStream)
        stream.hasNext() >> false

        when: "Ich einen Stream daraus erzeuge"
        Stream<DomainEventMessage> sequence = DomainEventStreamFactory.create(stream)

        then: "werde ich einen leere Stream erhalten"
        sequence == Stream.Empty.instance()
    }

    def "DomainEventStream mit einem Element"() {
        given:
        def stream = Mock(DomainEventStream)
        def message = new GenericDomainEventMessage(UUID.randomUUID(), 0, "Ich bin ein Ereignis")
        stream.hasNext() >> true >> false
        stream.peek() >> message

        when:
        def seq = DomainEventStreamFactory.create(stream)

        then:
        seq == Stream.of(message)
    }

    def "DomainEventStream mit vielen Elementen"() {
        given:
        def stream = Mock(DomainEventStream)

        def messages = [
                new GenericDomainEventMessage(UUID.randomUUID(), 0, "Ich bin das erste Ereignis"),
                new GenericDomainEventMessage(UUID.randomUUID(), 0, "Ich bin das zweite Ereignis")]

        stream.hasNext() >> true >> true >> false
        stream.peek() >> messages[0] >> messages[1]

        when:
        Stream<DomainEventMessage> seq = DomainEventStreamFactory.create(stream)

        then:
        seq.toJavaArray() == messages
    }
}