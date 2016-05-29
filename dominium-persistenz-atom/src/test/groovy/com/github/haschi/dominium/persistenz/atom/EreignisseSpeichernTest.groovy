package com.github.haschi.dominium.persistenz.atom

import com.github.haschi.dominium.modell.Domänenereignis
import com.github.haschi.dominium.modell.Version
import com.github.haschi.dominium.modell.Versionsbereich
import com.github.haschi.dominium.persistenz.atom.testaggregat.TestAggregat
import com.github.haschi.dominium.persistenz.atom.testaggregat.TestAggregatEreignisziel
import com.github.haschi.dominium.persistenz.atom.testaggregat.ZustandWurdeGeändert
import spock.lang.Specification
import spock.lang.Unroll

class EreignisseSpeichernTest extends Specification {

    UUID identitätsmerkmal = UUID.randomUUID();
    AtomEreignisLager<TestAggregatEreignisziel> lager = new AtomEreignisLager<>()

    def "Leeren Ereignis-Strom lesen"() {
        when:
        def ereignisliste = lager.getEreignisliste(identitätsmerkmal, Versionsbereich.ALLE_VERSIONEN)

        then:
        ereignisliste == []
    }

    @Unroll
    def "Ereignisse #anzahl in einem neuen Ereignis-Strom ablegen"(int anzahl) {
        given:
        TestAggregat aggregat = new TestAggregat(identitätsmerkmal, Version.NEU)
        aggregat.ereignisseErzeugen(anzahl)

        when:
        lager.neuenEreignisstromErzeugen(identitätsmerkmal, aggregat.getAggregatverwalter().getÄnderungen())
        List<Domänenereignis<TestAggregatEreignisziel>> ereignisliste = lager.getEreignisliste(
                identitätsmerkmal,
                Versionsbereich.ALLE_VERSIONEN)

        then:
        ereignisliste.size() == anzahl
        ereignisliste == (0..anzahl - 1).collect {new ZustandWurdeGeändert(it)}

        where:
        anzahl || _
        1              || _
        19             || _
        20             || _
        21             || _
        99             || _
        100            || _
        101            || _
    }

    def "Ereignisse in einem vorhandenen Ereignis-Strom speichern"() {
        given:
        lager.neuenEreignisstromErzeugen(identitätsmerkmal, [new ZustandWurdeGeändert(42L)])

        when:
        lager.ereignisseDemStromHinzufügen(identitätsmerkmal, 1L, [new ZustandWurdeGeändert(43L)])

        then:
        lager.getEreignisliste(identitätsmerkmal, Versionsbereich.ALLE_VERSIONEN) ==
                [new ZustandWurdeGeändert(42L), new ZustandWurdeGeändert(43)]
    }
}