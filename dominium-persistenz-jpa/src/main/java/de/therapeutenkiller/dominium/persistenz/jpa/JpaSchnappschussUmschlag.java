package de.therapeutenkiller.dominium.persistenz.jpa;

import de.therapeutenkiller.coding.aspekte.ValueObject;
import de.therapeutenkiller.dominium.persistenz.Umschlag;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Lob;
import java.io.IOException;

@Entity
@ValueObject
public class JpaSchnappschussUmschlag<S>
        implements Umschlag<S, JpaSchnappschussMetaDaten>  {

    @EmbeddedId
    private final JpaSchnappschussMetaDaten meta;

    @Column
    @Lob
    private final byte[] schnappshuss;

    @Column
    @Lob
    private String klasse;

    public  JpaSchnappschussUmschlag(
            final S snapshot,
            final JpaSchnappschussMetaDaten meta) {
        super();

        this.klasse = snapshot.getClass().getCanonicalName();
        this.meta = meta;

        try {
            this.schnappshuss = BinärSerialisierer.serialize(snapshot);
        } catch (IOException e) {
            throw new Serialisierungsfehler(e);
        }
    }

    protected JpaSchnappschussUmschlag() {
        super();
        this.meta = null;
        this.schnappshuss = null;
    }

    @Override
    public final JpaSchnappschussMetaDaten getMetaDaten() {
        return this.meta;
    }

    public final S öffnen() {
        try {
            final Class<?> k = Class.forName(this.klasse);
            return (S)BinärSerialisierer.deserialize(this.schnappshuss);
        } catch (final ClassNotFoundException | IOException e) {
            throw new Serialisierungsfehler(e);
        }
    }
}
