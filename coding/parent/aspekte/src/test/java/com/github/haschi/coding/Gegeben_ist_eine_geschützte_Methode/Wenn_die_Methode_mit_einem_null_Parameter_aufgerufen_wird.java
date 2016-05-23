package com.github.haschi.coding.Gegeben_ist_eine_geschützte_Methode;

import org.testng.annotations.Test;

/**
 * Created by m.haschka on 11/4/15.
 */
public class Wenn_die_Methode_mit_einem_null_Parameter_aufgerufen_wird {

    @Test
    public void dann_wird_keine_Ausnahme_ausgelöst() {
        this.geschützteMethode(null);
    }

    protected void geschützteMethode(final Object o) {
    }
}
