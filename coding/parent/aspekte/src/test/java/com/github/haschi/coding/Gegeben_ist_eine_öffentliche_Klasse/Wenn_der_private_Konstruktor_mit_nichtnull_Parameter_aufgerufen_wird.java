package com.github.haschi.coding.Gegeben_ist_eine_öffentliche_Klasse;

import org.testng.annotations.Test;

public class Wenn_der_private_Konstruktor_mit_nichtnull_Parameter_aufgerufen_wird {

    @Test
    public void dann_wird_keine_ArgumentIstNull_Ausnahme_geworfen() {
        new ÖffentlicheKlasse.Builder() .erzeugePerPrivatenKonstruktor();
    }
}
