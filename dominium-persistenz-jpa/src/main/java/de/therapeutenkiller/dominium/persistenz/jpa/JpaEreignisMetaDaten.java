package de.therapeutenkiller.dominium.persistenz.jpa;

import org.apache.commons.lang3.StringUtils;

import javax.persistence.Embeddable;
import java.io.Serializable;

@Embeddable
public final class JpaEreignisMetaDaten implements Serializable {

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
}
