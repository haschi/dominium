package com.github.haschi.dominium.modell

import com.github.haschi.dominium.testdomäne.TestAggregatEreignisZiel
import com.github.haschi.dominium.testdomäne.ZustandWurdeGeändert
import spock.lang.Specification

import java.util.stream.Collectors

class ÄnderungsverfolgungTest extends Specification {

    def "Eine neue Änderungsverfolgung besitzt die Versionsnummer NEU"() {
        expect:
        new Änderungsverfolgung<TestAggregatEreignisZiel>(Version.NEU).getVersion() == Version.NEU;
    }

    @SuppressWarnings("GroovyAssignabilityCheck")
    def "Änderungen führen zur Erhöhung der Version"() {

        given: "ich habe eine Änderungsverfolgung mit Version.NEU"
        Änderungsverfolgung<TestAggregatEreignisZiel> änderung = new Änderungsverfolgung<>(Version.NEU)

        when: "ich Ereignisse behandle"
        ereignisse.each { final ereignis -> änderung.falls ereignis }

        def versionswert = {long i, Version v = Version.NEU ->
            { -> i == 0L ? v : owner.call(i - 1, v.nachfolger())}()
        }

        then:
        änderung.version == versionswert(ereignisse.size())

        where:
        ereignisse                                                                                  || _
        []                                                                                          || _
        [ZustandWurdeGeändert.of(42L)]                                                              || _
        [ZustandWurdeGeändert.of(43L), ZustandWurdeGeändert.of(44L), ZustandWurdeGeändert.of(45L)]  || _
    }

    def "Ereignisse werden gespeichert"() {

        given: "ich habe eine Änderungsverfolgung"
        Änderungsverfolgung<TestAggregatEreignisZiel> änderung = new Änderungsverfolgung<>(Version.NEU)

        when: "ich Ereignisse behandle"
        ereignisse.each { final ereignis -> änderung.falls ereignis }

        then: "werde ich die gesammelten Ereignisse erhalten"
        änderung.alle({ereignis -> ereignis}).collect(Collectors.toList()) == ereignisse

        where:
        ereignisse                                                                                  || _
        []                                                                                          || _
        [ZustandWurdeGeändert.of(42L)]                                                              || _
        [ZustandWurdeGeändert.of(43L), ZustandWurdeGeändert.of(44L), ZustandWurdeGeändert.of(45L)]  || _
    }
}