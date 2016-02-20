package de.therapeutenkiller.haushaltsbuch.testsupport

import de.therapeutenkiller.haushaltsbuch.anwendungsfall.HaushaltsbuchführungBeginnen
import de.therapeutenkiller.haushaltsbuch.api.Kontoart
import de.therapeutenkiller.haushaltsbuch.api.kommando.HaushaltsbuchführungBeginnenKommando
import de.therapeutenkiller.haushaltsbuch.domaene.aggregat.Haushaltsbuch
import de.therapeutenkiller.haushaltsbuch.domaene.testsupport.HaushaltsbuchEreignislager
import de.therapeutenkiller.haushaltsbuch.domaene.testsupport.HaushaltsbuchMemoryRepository
import de.therapeutenkiller.haushaltsbuch.domaene.testsupport.TestUhr
import de.therapeutenkiller.haushaltsbuch.spi.HaushaltsbuchRepository
import spock.lang.Shared
import spock.lang.Specification

class HaushaltsbuchMemoryRepositoryTest extends Specification {

    @Shared TestUhr uhr = new TestUhr()

    def "X"() {
        given:
        HaushaltsbuchEreignislager lager = new HaushaltsbuchEreignislager(uhr);
        HaushaltsbuchRepository repository = new HaushaltsbuchMemoryRepository(lager)
        UUID haushaltsbuchId = UUID.randomUUID()
        new HaushaltsbuchführungBeginnen(repository).ausführen(
                new HaushaltsbuchführungBeginnenKommando(haushaltsbuchId))


        when:
        Haushaltsbuch haushaltsbuch2 = repository.suchen(haushaltsbuchId)
        haushaltsbuch2.neuesKontoHinzufügen("Girokonto", Kontoart.Aktiv)
        repository.speichern(haushaltsbuch2)

        then:
        repository.getStream(haushaltsbuchId).size() == 2
    }
}
