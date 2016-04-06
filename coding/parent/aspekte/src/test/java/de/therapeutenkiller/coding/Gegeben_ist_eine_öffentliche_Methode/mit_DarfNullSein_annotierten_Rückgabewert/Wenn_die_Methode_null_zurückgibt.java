package de.therapeutenkiller.coding.Gegeben_ist_eine_öffentliche_Methode
    .mit_DarfNullSein_annotierten_Rückgabewert;

import org.testng.annotations.Test;

import de.therapeutenkiller.coding.aspekte.DarfNullSein;

/**
 * Created by m.haschka on 11/4/15.
 */
public class Wenn_die_Methode_null_zurückgibt {

    @Test
    public void dann_wird_keine_Ausnahme_ausgelöst() {
        this.öffentlicheMitDarfNullSeinAnnotierteMethode();
    }

    @DarfNullSein
    public Object öffentlicheMitDarfNullSeinAnnotierteMethode() {
        return null;
    }
}
