package de.therapeutenkiller.dominium.persistenz.jpa;

import javax.persistence.Embeddable;

@Embeddable
public class JpaEreignisMetaDaten {

    private String name;

    private long version;

    public JpaEreignisMetaDaten(final String name, final long version) {

        this.name = name;
        this.version = version;
    }

    public JpaEreignisMetaDaten() {
        this.name = null;
        this.version = 0;
    }

    public final String getName() {
        return this.name;
    }

    public final long getVersion() {
        return this.version;
    }
}
