package de.therapeutenkiller.haushaltsbuch.support

import de.therapeutenkiller.haushaltsbuch.domaene.HaushaltsbuchWurdeAngelegt
import de.therapeutenkiller.haushaltsbuch.domaene.IchTueNichts
import de.therapeutenkiller.haushaltsbuch.domaene.aggregat.Haushaltsbuch
import de.therapeutenkiller.haushaltsbuch.domaene.support.DomainEvents
import spock.lang.Shared
import spock.lang.Specification
import spock.lang.Unroll


class DomainEventsTest extends Specification {

    @Shared boolean aufgerufen = false;

    @Unroll
    def "Registrierte Ereignishändler werden ausgeführt"() {

        given: "ein Ereignishändler ist registriert"
        aufgerufen = false
        DomainEvents.löschen()
        DomainEvents.registrieren(klasse, handler)

        when: "ein Domänenereignis ausgelöst wird"
        DomainEvents.auslösen(new HaushaltsbuchWurdeAngelegt(new Haushaltsbuch()));

        then: "wird der Ereignishändler aufgerufen worden sein"
        aufgerufen == match;

        where:
        klasse                           | handler                                                   | match
        HaushaltsbuchWurdeAngelegt.class | { HaushaltsbuchWurdeAngelegt event -> aufgerufen = true } | true
        IchTueNichts.class               | { IchTueNichts event -> aufgerufen = true }               | false
    }

    def "Nur Ereignishändler des zuständigen Typs werden ausgeführt"() {

        given: "Ein Ereignishändler des Typs IchTueNichts ist registriert"
        boolean aufgerufen = false;
        DomainEvents.registrieren(IchTueNichts.class, {IchTueNichts event -> aufgerufen = true})

        when:
        DomainEvents.auslösen(new HaushaltsbuchWurdeAngelegt(new Haushaltsbuch()));

        then:
        !aufgerufen
    }

    def "Mehrere Ereignishandler können registriert und ausgeführt werden."() {
        given: "2 IchTueNichts Ereignishändler, die Variable aufrufe um den Wert 1 bzw 2 erhöhen"
        def aufrufe = 0
        DomainEvents.registrieren(IchTueNichts, {IchTueNichts -> aufrufe = aufrufe + 1})
        DomainEvents.registrieren(IchTueNichts, {IchTueNichts -> aufrufe = aufrufe + 2})

        when: "das Ereignis IchTueNichts ausgelöst wird"
        DomainEvents.auslösen(new IchTueNichts(3))

        then:  "wird die Variable aufrufe den Wert 3 enthalten"
        aufrufe == 3
    }
}
