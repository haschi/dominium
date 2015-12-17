package de.therapeutenkiller.haushaltsbuch.domaene.support;

import de.therapeutenkiller.coding.aspekte.DarfNullSein;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

/**
 * Created by matthias on 17.12.15.
 */

// http://www.artima.com/lejava/articles/equality.html
public abstract class Wertobjekt {

    @Override
    public final boolean equals(@DarfNullSein final Object obj) {

        if (obj == this) {
            return true;
        }

        if (!(obj instanceof Wertobjekt)) {
            return false;
        }

        final Wertobjekt that = (Wertobjekt) obj;
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
