package de.therapeutenkiller.haushaltsbuch.domaene.testsupport;

import cucumber.api.Transformer;
import de.therapeutenkiller.haushaltsbuch.domaene.aggregat.Konto;

/**
 * Created by matthias on 23.12.15.
 */
public class KontoConverter extends Transformer<Konto> {

    @Override
    public Konto transform(String s) {
        return new Konto(s);
    }
}
