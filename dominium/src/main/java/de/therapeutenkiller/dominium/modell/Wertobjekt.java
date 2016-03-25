package de.therapeutenkiller.dominium.modell;

import de.therapeutenkiller.coding.aspekte.DarfNullSein;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

/**
 * Wertobjekte sind Daten, die nur anhand ihres Wertes
 * unterscheiden sind. Zwei Instanzen sind gleich, wenn
 * sie die gleichen Werte besitzen. Dabei ist es unerheblich,
 * ob das Wertobjekt ein oder mehr Datenfelder besitzt.
 *
 * Es ist ein Fehler, Klassen ohne Felder als Wertobjekt zu
 * kennzeichnen. Diese besitzen keine Werte, die verglichen
 * werden können.
 *
 * Wertobjekte sollten unveränderlich (immutable) sein.
 *
 */
public class Wertobjekt {

    @Override
    public final boolean equals(@DarfNullSein final Object that) {

        return that == this || that instanceof Wertobjekt && this.gleicht((Wertobjekt) that);
    }

    private boolean gleicht(final Wertobjekt that) {
        return that.canEqual(this) && EqualsBuilder.reflectionEquals(this, that, "id");
    }

    @Override
    public final int hashCode() {
        return HashCodeBuilder.reflectionHashCode(this, "id");
    }

    // s. http://www.artima.com/lejava/articles/equality.html
    private boolean canEqual(@DarfNullSein final Object other) {
        return other != null && this.getClass() == other.getClass();
    }
}
