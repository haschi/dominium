package de.therapeutenkiller.haushaltsbuch.domaene.aggregat;

import de.therapeutenkiller.haushaltsbuch.api.Kontoart;
import de.therapeutenkiller.haushaltsbuch.domaene.support.Wertobjekt;
import org.apache.commons.lang3.StringUtils;

public final class Konto extends Wertobjekt {

    public static final Konto ANFANGSBESTAND = new Konto("Anfangsbestand", new Buchungsregel());

    private final String kontoname;

    private final Buchungsregel regel;

    public Konto(final String kontoname, Buchungsregel regel) {

        super();
        this.regel = regel;

        if (StringUtils.isBlank(kontoname)) {
            throw new IllegalArgumentException("Der Kontoname darf nicht leer sein");
        }

        this.kontoname = kontoname;
    }

    public final String getBezeichnung() {
        return this.kontoname;
    }

    @Override
    public final String toString() {
        return "Konto{" + "kontoname='" + this.kontoname + '\'' + '}';
    }

    @SuppressWarnings("checkstyle")
    public boolean kannAusgabeBuchen(final Buchungssatz buchungssatz) {
        return true;
        // return this.kontoname.equals(buchungssatz.getSollkonto());
    }
}
