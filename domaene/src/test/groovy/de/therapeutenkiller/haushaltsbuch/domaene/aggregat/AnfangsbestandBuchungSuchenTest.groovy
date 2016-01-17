package de.therapeutenkiller.haushaltsbuch.domaene.aggregat
import spock.lang.Specification


class AnfangsbestandBuchungSuchenTest extends Specification {

    def "Suche nach Anfangsbestand-Buchung"() {

            given: "Angenommen ich habe ein Haushaltsbuch"
            def haushaltsbuch = new Haushaltsbuch(UUID.randomUUID())

            when: "Wenn ich den Anfangsbestand meines Girokontos buche"
            haushaltsbuch.neueBuchungHinzufügen("Girokonto", Konto.ANFANGSBESTAND.bezeichnung, 10.00.euro)

            then: "Dann wird mein Haushaltsbuch die Buchung für den Anfangsbestand besitzen"
            haushaltsbuch.istAnfangsbestandFürKontoVorhanden("Girokonto")
    }
}
