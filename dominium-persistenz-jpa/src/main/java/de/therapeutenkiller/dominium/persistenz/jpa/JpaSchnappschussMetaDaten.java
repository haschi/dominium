package de.therapeutenkiller.dominium.persistenz.jpa;

import javax.persistence.Embeddable;
import java.io.Serializable;
import java.time.LocalDateTime;

@Embeddable
public class JpaSchnappschussMetaDaten<I> implements Serializable {
    final I identitätsmerkmal;
    final LocalDateTime erstellungszeitpunkt;

    public JpaSchnappschussMetaDaten(final I identitätsmerkmal, final LocalDateTime jetzt) {
        this.identitätsmerkmal = identitätsmerkmal;
        this.erstellungszeitpunkt = jetzt;
    }

    protected JpaSchnappschussMetaDaten() {
        this.identitätsmerkmal = null;
        this.erstellungszeitpunkt = null;
    }
}