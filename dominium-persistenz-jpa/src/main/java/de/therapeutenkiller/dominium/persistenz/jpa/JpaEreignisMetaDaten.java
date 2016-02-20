package de.therapeutenkiller.dominium.persistenz.jpa;

import de.therapeutenkiller.dominium.modell.Wertobjekt;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.builder.ToStringBuilder;

import javax.persistence.Embeddable;
import java.io.Serializable;

@Embeddable
public final class JpaEreignisMetaDaten extends Wertobjekt implements Serializable {

    private String name;

    private long version;

    protected JpaEreignisMetaDaten() {
        this.name = StringUtils.EMPTY;
        this.version = 0;
    }

    public JpaEreignisMetaDaten(final String name, final long version) {

        this.name = name;
        this.version = version;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append("name", this.name)
                .append("version", this.version)
                .toString();
    }
}
