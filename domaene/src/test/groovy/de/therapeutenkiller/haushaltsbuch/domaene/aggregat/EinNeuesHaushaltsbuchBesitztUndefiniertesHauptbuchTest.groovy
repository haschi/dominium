package de.therapeutenkiller.haushaltsbuch.domaene.aggregat

import de.therapeutenkiller.dominium.modell.Version
import spock.lang.Specification

class EinNeuesHaushaltsbuchBesitztUndefiniertesHauptbuchTest extends Specification {

    def "Neues Haushaltsbuch wird mit undefiniertem Hauptbuch angelegt"() {
        UUID id = UUID.randomUUID()

        when: "wenn ich ein neues Haushaltsbuch anlege"
        Haushaltsbuch haushaltsbuch = new Haushaltsbuch(id, Version.NEU);

        then: "dann ist das Hauptbuch undefiniert"
        haushaltsbuch.istHauptbuchUndefiniert()
    }
}
