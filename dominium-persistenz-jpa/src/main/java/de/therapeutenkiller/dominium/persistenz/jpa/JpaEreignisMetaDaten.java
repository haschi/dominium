package de.therapeutenkiller.dominium.persistenz.jpa;

import de.therapeutenkiller.dominium.modell.Wertobjekt;
import org.apache.commons.lang3.builder.ToStringBuilder;

import javax.persistence.Embeddable;
import java.io.Serializable;

@Embeddable
public final class JpaEreignisMetaDaten<I> extends Wertobjekt implements Serializable {

    private I identitätsmerkmal;

    private long version;

    protected JpaEreignisMetaDaten() {
        this.identitätsmerkmal = null;
        this.version = 0;
    }

    public JpaEreignisMetaDaten(final I name, final long version) {

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
