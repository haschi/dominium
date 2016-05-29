package com.github.haschi.haushaltsbuch.testsupport

import com.github.haschi.haushaltsbuch.anwendungsfall.HaushaltsbuchführungBeginnen
import com.github.haschi.haushaltsbuch.api.Kontoart
import com.github.haschi.haushaltsbuch.api.kommando.BeginneHaushaltsbuchführung
import com.github.haschi.haushaltsbuch.domaene.aggregat.Haushaltsbuch
import com.github.haschi.haushaltsbuch.domaene.aggregat.Konto
import com.github.haschi.haushaltsbuch.domaene.aggregat.ereignis.HauptbuchWurdeAngelegt
import com.github.haschi.haushaltsbuch.domaene.aggregat.ereignis.JournalWurdeAngelegt
import com.github.haschi.haushaltsbuch.domaene.aggregat.ereignis.KontoWurdeAngelegt
import com.github.haschi.haushaltsbuch.domaene.testsupport.HaushaltsbuchEreignislager
import com.github.haschi.haushaltsbuch.domaene.testsupport.HaushaltsbuchMemoryRepository
import com.github.haschi.haushaltsbuch.domaene.testsupport.HaushaltsbuchMemorySchnappschussLager
import com.github.haschi.haushaltsbuch.domaene.testsupport.TestUhr
import com.github.haschi.haushaltsbuch.spi.HaushaltsbuchRepository
import spock.lang.Shared
import spock.lang.Specification

class HaushaltsbuchMemoryRepositoryTest extends Specification {

    @Shared TestUhr uhr = new TestUhr()

    def "X"() {
        given:
        HaushaltsbuchEreignislager lager = new HaushaltsbuchEreignislager(uhr);
        HaushaltsbuchMemorySchnappschussLager schnappschussLager = new HaushaltsbuchMemorySchnappschussLager()

        HaushaltsbuchRepository repository = new HaushaltsbuchMemoryRepository(lager, schnappschussLager)
        UUID haushaltsbuchId = UUID.randomUUID()

        new HaushaltsbuchführungBeginnen(repository).ausführen(
                new BeginneHaushaltsbuchführung(haushaltsbuchId))


        when:
        Haushaltsbuch haushaltsbuch2 = repository.suchen(haushaltsbuchId)
        haushaltsbuch2.neuesKontoHinzufügen("Girokonto", Kontoart.Aktiv)
        println(haushaltsbuch2.getÄnderungen())
        repository.speichern(haushaltsbuch2, haushaltsbuch2.identitätsmerkmal, haushaltsbuch2.getAggregatverwalter())

        then:
        repository.getStream(haushaltsbuchId) == [
                new HauptbuchWurdeAngelegt(haushaltsbuchId),
                new JournalWurdeAngelegt(haushaltsbuchId),
                new KontoWurdeAngelegt(Konto.ANFANGSBESTAND.bezeichnung, Kontoart.Aktiv),
                new KontoWurdeAngelegt("Girokonto", Kontoart.Aktiv)]
    }
}
