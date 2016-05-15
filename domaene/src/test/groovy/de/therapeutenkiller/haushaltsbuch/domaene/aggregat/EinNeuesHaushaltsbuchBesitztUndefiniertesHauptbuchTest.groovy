package de.therapeutenkiller.haushaltsbuch.domaene.aggregat

import spock.lang.Specification

class EinNeuesHaushaltsbuchBesitztUndefiniertesHauptbuchTest extends Specification {

    def "Neues Haushaltsbuch wird mit undefiniertem Hauptbuch angelegt"() {
        UUID id = UUID.randomUUID()

        when: "wenn ich ein neues Haushaltsbuch anlege"
        Haushaltsbuch haushaltsbuch = new Haushaltsbuch(id, 0L);

        then: "dann ist das Hauptbuch undefiniert"
        haushaltsbuch.istHauptbuchUndefiniert()
    }
}
