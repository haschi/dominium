package de.therapeutenkiller.haushaltsbuch.domaene.aggregat;

import de.therapeutenkiller.haushaltsbuch.domaene.support.Wertobjekt;
import org.apache.commons.lang3.StringUtils;

public final class Konto extends Wertobjekt {

    public static final Konto ANFANGSBESTAND = new Konto("Anfangsbestand");

    private final String kontoname;

    public Konto(final String kontoname) {

        super();

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
}
