package de.therapeutenkiller.dominium.persistenz.jpa;

import javax.persistence.Embeddable;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.UUID;

@Embeddable
public final class JpaSchnappschussMetaDaten implements Serializable {
    final UUID identitätsmerkmal;
    final LocalDateTime erstellungszeitpunkt;

    public JpaSchnappschussMetaDaten(final UUID identitätsmerkmal, final LocalDateTime jetzt) {
        this.identitätsmerkmal = identitätsmerkmal;
        this.erstellungszeitpunkt = jetzt;
    }

    protected JpaSchnappschussMetaDaten() {
        this.identitätsmerkmal = null;
        this.erstellungszeitpunkt = null;
    }
}