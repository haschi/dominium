package com.github.haschi.coding.Gegeben_ist_eine_öffentliche_statische_Methode;

import org.testng.annotations.Test;

import com.github.haschi.coding.aspekte.ArgumentIstNullException;

public final class Wenn_die_Methode_mit_einem_null_Parameter_aufgerufen_wird {

    @Test(expectedExceptions = ArgumentIstNullException.class)
    public void dann_wird_eine_ArgumentIstNullException_ausgelöst() {
        öffentlicheStatischeMethode(null);
    }

    public static void öffentlicheStatischeMethode(final Object einWert) {
    }
}
