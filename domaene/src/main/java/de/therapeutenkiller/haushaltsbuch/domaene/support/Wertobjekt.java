package de.therapeutenkiller.haushaltsbuch.domaene.support;

import de.therapeutenkiller.coding.aspekte.DarfNullSein;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

// s. http://www.artima.com/lejava/articles/equality.html
public class Wertobjekt {

    @Override
    public final boolean equals(@DarfNullSein final Object that) {

        if (that == this) {
            return true;
        }

        if (!(that instanceof Wertobjekt)) {
            return false;
        }

        return this.gleicht((Wertobjekt) that);
    }

    private final boolean gleicht(final Wertobjekt that) {
        return that.canEqual(this) && EqualsBuilder.reflectionEquals(this, that);
    }

    @Override
    public final int hashCode() {
        return HashCodeBuilder.reflectionHashCode(this);
    }

    public final boolean canEqual(@DarfNullSein final Object other) {
        return other != null && this.getClass() == other.getClass();
    }
}
