package de.therapeutenkiller.haushaltsbuch.domaene.support;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

public class Entität<T> {
    private T identität;

    protected Entität(final T identität) {

        this.identität = identität;
    }

    public final T getIdentität() {
        return this.identität;
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
            .append(this.identität, entität.identität)
            .isEquals();
    }

    @Override public final int hashCode() {
        return new HashCodeBuilder(17, 37)
            .append(this.identität)
            .toHashCode();
    }

    protected final void setIdentity(final T identity) {
        this.identität = identity;
    }
}
