package com.github.haschi.dominium.persistenz.jpa;

import com.github.haschi.coding.aspekte.ValueObject;
import org.apache.commons.lang3.builder.ToStringBuilder;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;
import java.util.UUID;

@Embeddable
@ValueObject
public class JpaEreignisMetaDaten implements Serializable {

    private static final long serialVersionUID = 7136630060440710008L;

    @Column(columnDefinition = "BINARY(16)")
    private final UUID identitätsmerkmal;

    private final long version;

    protected JpaEreignisMetaDaten() {

        super();

        this.identitätsmerkmal = null;
        this.version = 0;
    }

    JpaEreignisMetaDaten(final UUID name, final long version) {

        super();

        this.identitätsmerkmal = name;
        this.version = version;
    }

    @Override
    public final String toString() {
        return new ToStringBuilder(this)
                .append("identitätsmerkmal", this.identitätsmerkmal)
                .append("version", this.version)
                .toString();
    }
}
