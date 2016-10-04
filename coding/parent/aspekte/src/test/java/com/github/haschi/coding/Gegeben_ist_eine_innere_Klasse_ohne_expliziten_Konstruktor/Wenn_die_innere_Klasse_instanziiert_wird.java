package com.github.haschi.coding.Gegeben_ist_eine_innere_Klasse_ohne_expliziten_Konstruktor;

import org.testng.annotations.Test;

public class Wenn_die_innere_Klasse_instanziiert_wird
{

    @Test
    public void dann_wird_keine_ArgumentIstNullException_ausgelöst()
    {
        new InnereKlasse();
    }

    private static class InnereKlasse
    {
    }
}
