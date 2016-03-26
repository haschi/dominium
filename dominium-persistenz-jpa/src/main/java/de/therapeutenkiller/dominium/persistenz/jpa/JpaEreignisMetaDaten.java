package de.therapeutenkiller.dominium.persistenz.jpa;

import de.therapeutenkiller.dominium.modell.Wertobjekt;
import org.apache.commons.lang3.builder.ToStringBuilder;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;
import java.util.UUID;

@Embeddable
public final class JpaEreignisMetaDaten extends Wertobjekt implements Serializable {

    @Column(columnDefinition = "BINARY(16)")
    private UUID identitätsmerkmal;

    private long version;

    protected JpaEreignisMetaDaten() {
        this.identitätsmerkmal = null;
        this.version = 0;
    }

    public JpaEreignisMetaDaten(final UUID name, final long version) {

        this.identitätsmerkmal = name;
        this.version = version;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append("identitätsmerkmal", this.identitätsmerkmal)
                .append("version", this.version)
                .toString();
    }
}
