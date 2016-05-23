package com.github.haschi.coding.Gegeben_ist_eine_öffentliche_Methode;

import org.testng.annotations.Test;

/**
 * Created by m.haschka on 11/4/15.
 */
public class Wenn_die_Methode_mit_nichtnull_Parameter_aufgerufen_wird {

    @Test
    public void dann_wird_keine_Ausnahme_ausgelöst() {
        this.öffentlicheMethode("Hello World");
    }

    public void öffentlicheMethode(final Object s) {
    }
}
