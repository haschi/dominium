package de.therapeutenkiller.haushaltsbuch.domaene.ereignis;

import de.therapeutenkiller.haushaltsbuch.domaene.support.Wertobjekt;

public final class BuchungWurdeNichtAusgeführt extends Wertobjekt {
    public final String fehlermeldung;

    public BuchungWurdeNichtAusgeführt(final String fehlermeldung) {

        super();
        this.fehlermeldung = fehlermeldung;
    }
}
