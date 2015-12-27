package de.therapeutenkiller.haushaltsbuch.domaene.anwendungsfall;

import javax.money.MonetaryAmount;
import java.util.UUID;

public class KontoAnlegenMitAnfangsbestandKommando {

    public final UUID haushaltsbuch;
    public final String kontoname;
    public final MonetaryAmount betrag;

    public KontoAnlegenMitAnfangsbestandKommando(
            final UUID haushaltsbuch,
            final String kontoname,
            final MonetaryAmount betrag) {
        this.haushaltsbuch = haushaltsbuch;
        this.kontoname = kontoname;
        this.betrag = betrag;
    }
}
