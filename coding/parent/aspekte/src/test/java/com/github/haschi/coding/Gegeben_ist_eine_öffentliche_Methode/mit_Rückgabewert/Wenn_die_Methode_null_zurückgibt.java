package com.github.haschi.coding.Gegeben_ist_eine_öffentliche_Methode.mit_Rückgabewert;

import com.github.haschi.coding.aspekte.RückgabewertIstNullException;
import org.testng.annotations.Test;

public class Wenn_die_Methode_null_zurückgibt
{

    @Test(expectedExceptions = RückgabewertIstNullException.class)
    public void dann_wird_eine_RückgabewertIstNullException_ausgelöst()
    {
        this.öffentlicheMethodeMitRückgabewert();
    }

    public Object öffentlicheMethodeMitRückgabewert()
    {
        return null;
    }
}
