package de.therapeutenkiller.coding.Gegeben_ist_eine_öffentliche_Klasse;

import de.therapeutenkiller.coding.aspekte.ArgumentIstNullException;
import org.testng.annotations.Test;

/**
 * Created by m.haschka on 11/4/15.
 */
public class Wenn_der_paketprivate_Konstruktor_mit_null_Parameter_aufgerufen_wird {

    @Test(expectedExceptions = ArgumentIstNullException.class)
    public void dann_wird_eine_ArgumentIstNullException_ausgelöst() {
        new ÖffentlicheKlasse(null, null);
    }
}
