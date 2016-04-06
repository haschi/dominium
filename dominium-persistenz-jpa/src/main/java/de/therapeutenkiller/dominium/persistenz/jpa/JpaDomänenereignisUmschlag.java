package de.therapeutenkiller.dominium.persistenz.jpa;

import de.therapeutenkiller.dominium.modell.Wertobjekt;
import de.therapeutenkiller.dominium.persistenz.Umschlag;
import org.apache.commons.lang3.builder.ToStringBuilder;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.OneToOne;

/**
 * Ein DomänenereignisUmschlag für Domänenereignisse zum Speichern in einer
 * Datenbank mit JPA.
  */
@Entity
public class JpaDomänenereignisUmschlag<E>
        extends Wertobjekt
        implements Umschlag<E, JpaEreignisMetaDaten> {

    @EmbeddedId
    private JpaEreignisMetaDaten meta;

    @OneToOne(targetEntity = JpaDomänenereignis.class)
    public E ereignis;

    public JpaDomänenereignisUmschlag(
            final E ereignis,
            final JpaEreignisMetaDaten meta) {
        super();

        this.meta = meta;
        this.ereignis = ereignis;
    }

    public JpaDomänenereignisUmschlag() {
        super();
    }

    public final E getEreignis() {
        return this.ereignis;
    }

    @Override
    public final JpaEreignisMetaDaten getMetaDaten() {
        return this.meta;
    }

    @Override
    public final E öffnen() {
        return this.getEreignis();
    }

    @Override
    public final String toString() {
        return new ToStringBuilder(this)
                .append("meta", this.meta)
                .toString();
    }
}
