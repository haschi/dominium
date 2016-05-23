package com.github.haschi.dominium.persistenz.jpa;

import com.github.haschi.coding.aspekte.ValueObject;
import com.github.haschi.dominium.persistenz.Umschlag;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Lob;
import java.io.IOException;

/**
 * Ein DomänenereignisUmschlag für Domänenereignisse zum Speichern in einer
 * Datenbank mit JPA.
  */
@Entity
@ValueObject
public class JpaDomänenereignisUmschlag<E>
        implements Umschlag<E, JpaEreignisMetaDaten> {

    @EmbeddedId
    private JpaEreignisMetaDaten meta;

    @Column
    @Lob
    private byte[] ereignis;

    public JpaDomänenereignisUmschlag(
            final E ereignis,
            final JpaEreignisMetaDaten meta) {
        super();

        this.meta = meta;

        try {
            this.ereignis = BinärSerialisierer.serialize(ereignis);
        } catch (IOException e) {
            throw new Serialisierungsfehler(e);
        }
    }

    public JpaDomänenereignisUmschlag() {
        super();
    }

    @Override
    public final JpaEreignisMetaDaten getMetaDaten() {
        return this.meta;
    }

    @Override
    public final E öffnen() {
        try {
            return (E)BinärSerialisierer.deserialize(ereignis);
        } catch (final ClassNotFoundException | IOException e) {
            throw new Serialisierungsfehler(e);
        }
    }
}
