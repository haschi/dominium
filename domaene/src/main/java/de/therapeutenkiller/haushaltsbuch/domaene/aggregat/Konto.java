package de.therapeutenkiller.haushaltsbuch.domaene.aggregat;

import de.therapeutenkiller.coding.aspekte.DarfNullSein;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

public final class Konto {

    public static final Konto ANFANGSBESTAND = new Konto("Anfangsbestand", new KeineRegel());

    private final String kontoname;

    private final Buchungsregel regel;

    public Konto(final String kontoname, final Buchungsregel regel) {

        super();
        this.regel = regel;

        if (StringUtils.isBlank(kontoname)) {
            throw new IllegalArgumentException("Der Kontoname darf nicht leer sein");
        }

        this.kontoname = kontoname;
    }

    public String getBezeichnung() {
        return this.kontoname;
    }

    @Override
    public String toString() {
        return "Konto{" + "kontoname='" + this.kontoname + '\'' + '}';
    }

    @SuppressWarnings("checkstyle")
    public boolean kannAusgabeBuchen(final Buchungssatz buchungssatz) {
        return this.regel.kannAusgabeBuchen(buchungssatz);
    }

    @Override
    public boolean equals(@DarfNullSein final Object object) {
        if (this == object) {
            return true;
        }

        if (!(object instanceof Konto)) {
            return false;
        }

        final Konto konto = (Konto) object;

        return new EqualsBuilder()
                .append(this.kontoname, konto.kontoname)
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37)
                .append(this.kontoname)
                .toHashCode();
    }
}
