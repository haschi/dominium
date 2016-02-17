package de.therapeutenkiller.haushaltsbuch.testsupport

import de.therapeutenkiller.haushaltsbuch.anwendungsfall.HaushaltsbuchführungBeginnen
import de.therapeutenkiller.haushaltsbuch.api.Kontoart
import de.therapeutenkiller.haushaltsbuch.api.kommando.HaushaltsbuchführungBeginnenKommando
import de.therapeutenkiller.haushaltsbuch.domaene.aggregat.Haushaltsbuch
import de.therapeutenkiller.haushaltsbuch.domaene.testsupport.HaushaltsbuchEreignislager
import de.therapeutenkiller.haushaltsbuch.domaene.testsupport.HaushaltsbuchMemoryRepository
import de.therapeutenkiller.haushaltsbuch.domaene.testsupport.TestUhr
import spock.lang.Shared
import spock.lang.Specification

class HaushaltsbuchMemoryRepositoryTest extends Specification {

    @Shared TestUhr uhr = new TestUhr()

    def "X"() {
        given:
        HaushaltsbuchEreignislager lager = new HaushaltsbuchEreignislager(uhr);
        HaushaltsbuchMemoryRepository repository = new HaushaltsbuchMemoryRepository(lager)

        new HaushaltsbuchführungBeginnen(repository).ausführen(
                new HaushaltsbuchführungBeginnenKommando())

        UUID uuid = repository.aktuell

        when:
        Haushaltsbuch haushaltsbuch2 = repository.findBy(uuid)
        haushaltsbuch2.neuesKontoHinzufügen("Girokonto", Kontoart.Aktiv)
        repository.save(haushaltsbuch2)

        then:
        repository.getStream(uuid).size() == 2
    }
}
