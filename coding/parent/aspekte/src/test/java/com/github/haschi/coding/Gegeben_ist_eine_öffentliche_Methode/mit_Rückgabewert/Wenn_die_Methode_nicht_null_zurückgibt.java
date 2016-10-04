package com.github.haschi.coding.Gegeben_ist_eine_öffentliche_Methode.mit_Rückgabewert;

public class Wenn_die_Methode_nicht_null_zurückgibt
{

    public void dann_wird_keine_Ausnahme_ausgelöst()
    {
        this.methodeMitRückgabewert();
    }

    public Object methodeMitRückgabewert()
    {
        return "Hello World";
    }
}
