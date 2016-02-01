package de.therapeutenkiller.dominium.jpa;

import javax.persistence.Embeddable;

@Embeddable
public class JpaEreignisMetaDaten {

    private final String name;
    private final long version;

    public JpaEreignisMetaDaten(final String name, final long version) {

        this.name = name;
        this.version = version;
    }

    public JpaEreignisMetaDaten() {
        this.name = null;
        this.version = 0;
    }
}
