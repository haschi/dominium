package de.therapeutenkiller.coding.Gegeben_ist_eine_öffentliche_Methode.mit_Rückgabewert;

import org.testng.annotations.Test;

import de.therapeutenkiller.coding.aspekte.RückgabewertIstNullException;

/**
 * Created by m.haschka on 11/4/15.
 */
public class Wenn_die_Methode_null_zurückgibt {

    @Test(expectedExceptions = RückgabewertIstNullException.class)
    public void dann_wird_eine_RückgabewertIstNullException_ausgelöst() {
        this.öffentlicheMethodeMitRückgabewert();
    }

    public Object öffentlicheMethodeMitRückgabewert() {
        return null;
    }
}
