package de.therapeutenkiller.coding.Gegeben_ist_eine_öffentliche_statische_Methode;

import org.testng.annotations.Test;

import de.therapeutenkiller.coding.aspekte.ArgumentIstNullException;

/**
 * Created by m.haschka on 11/4/15.
 */
public class Wenn_die_Methode_mit_einem_null_Parameter_aufgerufen_wird {

    @Test(expectedExceptions = ArgumentIstNullException.class)
    public void dann_wird_eine_ArgumentIstNullException_ausgelöst() {
        öffentlicheStatischeMethode(null);
    }

    public static void öffentlicheStatischeMethode(Object o) {
    }
}
