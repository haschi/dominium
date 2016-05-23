package com.github.haschi.dominium.persistenz.jpa;

import com.github.haschi.coding.aspekte.ValueObject;
import com.github.haschi.dominium.persistenz.Umschlag;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Lob;
import java.io.IOException;

@Entity
@ValueObject
public class JpaSchnappschussUmschlag<S>
        implements Umschlag<S, JpaSchnappschussMetaDaten> {

    @EmbeddedId
    private final JpaSchnappschussMetaDaten meta;

    @Column
    @Lob
    private final byte[] schnappschuss;

    public  JpaSchnappschussUmschlag(
            final S snapshot,
            final JpaSchnappschussMetaDaten meta) {
        super();

        this.meta = meta;

        try {
            this.schnappschuss = BinärSerialisierer.serialize(snapshot);
        } catch (IOException e) {
            throw new Serialisierungsfehler(e);
        }
    }

    protected JpaSchnappschussUmschlag() {
        super();
        this.meta = null;
        this.schnappschuss = null;
    }

    @Override
    public final JpaSchnappschussMetaDaten getMetaDaten() {
        return this.meta;
    }

    public final S öffnen() {
        try {
            return (S)BinärSerialisierer.deserialize(this.schnappschuss);
        } catch (final ClassNotFoundException | IOException e) {
            throw new Serialisierungsfehler(e);
        }
    }
}
