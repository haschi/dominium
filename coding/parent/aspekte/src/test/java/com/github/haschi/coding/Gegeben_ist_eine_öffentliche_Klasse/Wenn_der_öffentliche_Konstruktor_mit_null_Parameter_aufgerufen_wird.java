package com.github.haschi.coding.Gegeben_ist_eine_öffentliche_Klasse;

import org.testng.annotations.Test;

import com.github.haschi.coding.aspekte.ArgumentIstNullException;

public final class Wenn_der_öffentliche_Konstruktor_mit_null_Parameter_aufgerufen_wird {

    @Test(expectedExceptions = ArgumentIstNullException.class)
    public void dann_wird_eine_ArgumentIstNullException_ausgelöst() {
        new ÖffentlicheKlasse(null);
    }
}
