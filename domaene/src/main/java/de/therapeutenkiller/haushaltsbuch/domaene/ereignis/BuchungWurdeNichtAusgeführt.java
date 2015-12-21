package de.therapeutenkiller.haushaltsbuch.domaene.ereignis;

import de.therapeutenkiller.haushaltsbuch.domaene.support.Wertobjekt;

/**
 * Created by matthias on 21.12.15.
 */
public final class BuchungWurdeNichtAusgeführt extends Wertobjekt {
    public final String fehlermeldung;

    public BuchungWurdeNichtAusgeführt(final String fehlermeldung) {

        super();
        this.fehlermeldung = fehlermeldung;
    }
}
