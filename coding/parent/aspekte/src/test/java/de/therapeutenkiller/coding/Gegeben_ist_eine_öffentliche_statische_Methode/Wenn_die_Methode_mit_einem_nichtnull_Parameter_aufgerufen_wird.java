package de.therapeutenkiller.coding.Gegeben_ist_eine_öffentliche_statische_Methode;

import org.testng.annotations.Test;

/**
 * Created by m.haschka on 11/4/15.
 */
public class Wenn_die_Methode_mit_einem_nichtnull_Parameter_aufgerufen_wird {

    @Test
    public void dann_wird_keine_Ausnahme_ausgelöst() {
        öffentlicheStatischeMethode("Hello World");
    }

    public static void öffentlicheStatischeMethode(String s) {
    }
}
