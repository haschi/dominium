package de.therapeutenkiller.dominium.persistenz.jpa;

import de.therapeutenkiller.dominium.modell.Wertobjekt;
import de.therapeutenkiller.dominium.persistenz.Umschlag;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.OneToOne;

@Entity
public class JpaSchnappschussUmschlag<S>
        extends Wertobjekt
        implements Umschlag<S, JpaSchnappschussMetaDaten>  {

    @EmbeddedId
    private final JpaSchnappschussMetaDaten meta;

    @OneToOne(targetEntity = JpaSchnappschuss.class)
    public S ereignis;

    public  JpaSchnappschussUmschlag(
            final S snapshot,
            final JpaSchnappschussMetaDaten meta) {

        this.meta = meta;
        this.ereignis = snapshot;

    }

    protected JpaSchnappschussUmschlag() {
        this.meta = null;
        this.ereignis = null;
    }

    @Override
    public final JpaSchnappschussMetaDaten getMetaDaten() {
        return this.meta;
    }

    public final S öffnen() {
        return this.ereignis;
    }
}
