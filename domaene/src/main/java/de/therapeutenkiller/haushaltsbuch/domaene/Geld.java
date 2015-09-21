package de.therapeutenkiller.haushaltsbuch.domaene;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

public final class Geld {
    private final Integer betrag;
    private final String währung;

    public Geld(Integer einBetrag, String eineWährung) {
        this.betrag = einBetrag;
        this.währung = eineWährung;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;

        if (!(o instanceof Geld)) return false;

        Geld geld = (Geld) o;

        return new EqualsBuilder()
                .append(betrag, geld.betrag)
                .append(währung, geld.währung)
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37)
                .append(betrag)
                .append(währung)
                .toHashCode();
    }

    @Override
    public String toString() {
        return betrag.toString() + " " + währung;
    }

    public Geld hinzufügen(Geld einGeld) {
        return new Geld(this.betrag + einGeld.betrag, this.währung);
    }
}
