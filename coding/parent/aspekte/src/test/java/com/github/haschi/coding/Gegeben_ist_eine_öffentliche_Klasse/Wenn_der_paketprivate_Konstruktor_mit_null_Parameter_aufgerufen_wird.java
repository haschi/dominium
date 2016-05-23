package com.github.haschi.coding.Gegeben_ist_eine_öffentliche_Klasse;

import com.github.haschi.coding.aspekte.ArgumentIstNullException;
import org.testng.annotations.Test;

public final class Wenn_der_paketprivate_Konstruktor_mit_null_Parameter_aufgerufen_wird {

    @Test(expectedExceptions = ArgumentIstNullException.class)
    public void dann_wird_eine_ArgumentIstNullException_ausgelöst() {
        new ÖffentlicheKlasse(null, null);
    }
}
