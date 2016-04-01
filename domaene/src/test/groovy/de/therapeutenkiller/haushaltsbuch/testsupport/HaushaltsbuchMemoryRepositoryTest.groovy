package de.therapeutenkiller.haushaltsbuch.testsupport

import de.therapeutenkiller.haushaltsbuch.anwendungsfall.HaushaltsbuchführungBeginnen
import de.therapeutenkiller.haushaltsbuch.api.Kontoart
import de.therapeutenkiller.haushaltsbuch.api.kommando.BeginneHaushaltsbuchführung
import de.therapeutenkiller.haushaltsbuch.domaene.aggregat.Haushaltsbuch
import de.therapeutenkiller.haushaltsbuch.domaene.aggregat.Konto
import de.therapeutenkiller.haushaltsbuch.domaene.aggregat.ereignis.HauptbuchWurdeAngelegt
import de.therapeutenkiller.haushaltsbuch.domaene.aggregat.ereignis.JournalWurdeAngelegt
import de.therapeutenkiller.haushaltsbuch.domaene.aggregat.ereignis.KontoWurdeAngelegt
import de.therapeutenkiller.haushaltsbuch.domaene.testsupport.HaushaltsbuchEreignislager
import de.therapeutenkiller.haushaltsbuch.domaene.testsupport.HaushaltsbuchMemoryRepository
import de.therapeutenkiller.haushaltsbuch.domaene.testsupport.HaushaltsbuchMemorySchnappschussLager
import de.therapeutenkiller.haushaltsbuch.domaene.testsupport.TestUhr
import de.therapeutenkiller.haushaltsbuch.spi.HaushaltsbuchRepository
import spock.lang.Shared
import spock.lang.Specification

class HaushaltsbuchMemoryRepositoryTest extends Specification {

    @Shared TestUhr uhr = new TestUhr()

    def "X"() {
        given:
        HaushaltsbuchEreignislager lager = new HaushaltsbuchEreignislager(uhr);
        HaushaltsbuchMemorySchnappschussLager schnapschussLager = new HaushaltsbuchMemorySchnappschussLager()

        HaushaltsbuchRepository repository = new HaushaltsbuchMemoryRepository(lager, schnapschussLager)
        UUID haushaltsbuchId = UUID.randomUUID()

        new HaushaltsbuchführungBeginnen(repository).ausführen(
                new BeginneHaushaltsbuchführung(haushaltsbuchId))


        when:
        Haushaltsbuch haushaltsbuch2 = repository.suchen(haushaltsbuchId)
        haushaltsbuch2.neuesKontoHinzufügen("Girokonto", Kontoart.Aktiv)
        repository.speichern(haushaltsbuch2)

        then:
        repository.getStream(haushaltsbuchId) == [
            new HauptbuchWurdeAngelegt(haushaltsbuchId),
            new JournalWurdeAngelegt(haushaltsbuchId),
            new KontoWurdeAngelegt(Konto.ANFANGSBESTAND.bezeichnung, Kontoart.Aktiv),
            new KontoWurdeAngelegt("Girokonto", Kontoart.Aktiv)]
    }
}
