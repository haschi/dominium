package com.github.haschi.coding.Gegeben_ist_eine_öffentliche_Methode.mit_DarfNullSein_annotiertem_Parameter;

import com.github.haschi.coding.aspekte.DarfNullSein;
import org.testng.annotations.Test;

public class Wenn_die_Methode_mit_einem_null_Parameter_aufgerufen_wird
{

    @Test
    public void dann_wird_keine_Ausnahme_asugelöst()
    {
        this.öffentlicheMethodeMitDarfNullSeinParameter(null);
    }

    public void öffentlicheMethodeMitDarfNullSeinParameter(@DarfNullSein final Object o)
    {
    }
}
