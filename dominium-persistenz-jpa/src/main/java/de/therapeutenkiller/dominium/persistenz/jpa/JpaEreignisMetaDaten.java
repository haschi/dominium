package de.therapeutenkiller.dominium.persistenz.jpa;

import javax.persistence.Embeddable;
import java.io.Serializable;

@Embeddable
public final class JpaEreignisMetaDaten implements Serializable {

    private String name;

    private long version;

    public JpaEreignisMetaDaten(final String name, final long version) {

        this.name = name;
        this.version = version;
    }
}
