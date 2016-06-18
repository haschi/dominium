package com.github.haschi.dominium.modell

import com.github.haschi.dominium.testdomaene.ImmutableBearbeitungWurdeBeendet
import com.github.haschi.dominium.testdomaene.ImmutableBeendeBearbeitung
import com.github.haschi.dominium.testdomaene.ImmutableZustandWurdeGeaendert
import com.github.haschi.dominium.testdomaene.generiert.ImmutableBearbeitungWurdeBeendetMessage
import com.github.haschi.dominium.testdomaene.generiert.ImmutableZustandWurdeGeaendertMessage
import com.github.haschi.dominium.testdomaene.generiert.TestAggregatEvent
import com.github.haschi.dominium.testdomaene.generiert.TestAggregatProxy
import spock.lang.Shared
import spock.lang.Specification

class AggregatwurzelProxyTest extends Specification {

    @Shared UUID identitätsmerkmal = UUID.randomUUID()

    def "Die Ereignisse einer Aggregatwurzel werden aufgezeichnet"() {
        given: "ich habe eine Aggregatwurzel"
        final TestAggregatProxy aggregat = new TestAggregatProxy(identitätsmerkmal, Version.NEU)

        when: "ich den Zustand des Aggregats ändere"
        zustandsänderungen.each {änderung -> aggregat.zustandÄndern(änderung)}

        then: "werde ich den Zustand des Aggregats ändern"
        aggregat.uncommittedChanges ==  ereignisse

        where:
        zustandsänderungen  || ereignisse
        []                  || []
        [42L]               || [ImmutableZustandWurdeGeaendertMessage.of(ImmutableZustandWurdeGeaendert.of(42L))]
        [43L, 44L, 45L]     || [ImmutableZustandWurdeGeaendertMessage.of(ImmutableZustandWurdeGeaendert.of(43L)), ImmutableZustandWurdeGeaendertMessage.of(ImmutableZustandWurdeGeaendert.of(44L)), ImmutableZustandWurdeGeaendertMessage.of(ImmutableZustandWurdeGeaendert.of(45L))]
    }

    def "Ein Aggregat kann aus Ereignissen wiederhergestellt werden"() {
        given: "ich habe ein Aggregat"
        final TestAggregatProxy proxy = new TestAggregatProxy(identitätsmerkmal, Version.NEU)

        final List<TestAggregatEvent> ereignisse = [
                ImmutableZustandWurdeGeaendertMessage.of(ImmutableZustandWurdeGeaendert.of(42L))
        ]

        when:
        proxy.wiederherstellen(ereignisse)
        proxy.nächsterZustand()

        then:
        proxy.uncommittedChanges == [ImmutableZustandWurdeGeaendertMessage.of(ImmutableZustandWurdeGeaendert.of(43L))]
    }

    def "Aggregate können verschiedene Ereignisse erzeugen"() {
        given:
        final TestAggregatProxy aggregat = new TestAggregatProxy(identitätsmerkmal, Version.NEU)

        when:
        aggregat.zustandÄndern(42L);
        aggregat.bearbeitungBeenden(ImmutableBeendeBearbeitung.of(identitätsmerkmal));

        then:
        aggregat.uncommittedChanges == [ImmutableZustandWurdeGeaendertMessage.of(ImmutableZustandWurdeGeaendert.of(42)), ImmutableBearbeitungWurdeBeendetMessage.of(ImmutableBearbeitungWurdeBeendet.of())]
    }
}