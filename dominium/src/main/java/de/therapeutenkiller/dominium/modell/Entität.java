package de.therapeutenkiller.dominium.modell;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

/**
 * Entitäten sind Objekte mit eindeutiger Identität. Die Identität
 * des Objekts wird durch das Identitätsmerkmal hergestellt. Das
 * Identitätsmerkmal muss ein Wertobjekt sein.
 *
 * Jeweils zwei Entitäten sind gleich, wenn sie das gleiche
 * Identitätsmerkmal besitzen.
 *
 * @param <T> Der Typ des Identitätsmerkmals
 */
public class Entität<T> {
    private final T identitätsmerkmal;

    protected Entität(final T identitätsmerkmal) {
        this.identitätsmerkmal = identitätsmerkmal;
    }

    public final T getIdentitätsmerkmal() {
        return this.identitätsmerkmal;
    }

    @Override public final boolean equals(final Object object) {
        if (this == object) {
            return true;
        }

        if (!(object instanceof Entität<?>)) {
            return false;
        }

        final Entität<?> entität = (Entität<?>) object;

        return new EqualsBuilder()
            .append(this.identitätsmerkmal, entität.identitätsmerkmal)
            .isEquals();
    }

    @Override public final int hashCode() {
        return new HashCodeBuilder(17, 37)
            .append(this.identitätsmerkmal)
            .toHashCode();
    }
}
