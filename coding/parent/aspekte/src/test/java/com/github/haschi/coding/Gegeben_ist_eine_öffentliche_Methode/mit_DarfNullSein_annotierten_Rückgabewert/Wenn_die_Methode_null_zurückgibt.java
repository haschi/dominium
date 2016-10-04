package com.github.haschi.coding.Gegeben_ist_eine_öffentliche_Methode.mit_DarfNullSein_annotierten_Rückgabewert;

import com.github.haschi.coding.aspekte.DarfNullSein;
import org.testng.annotations.Test;

public class Wenn_die_Methode_null_zurückgibt
{

    @Test
    public void dann_wird_keine_Ausnahme_ausgelöst()
    {
        this.öffentlicheMitDarfNullSeinAnnotierteMethode();
    }

    @DarfNullSein
    public Object öffentlicheMitDarfNullSeinAnnotierteMethode()
    {
        return null;
    }
}
