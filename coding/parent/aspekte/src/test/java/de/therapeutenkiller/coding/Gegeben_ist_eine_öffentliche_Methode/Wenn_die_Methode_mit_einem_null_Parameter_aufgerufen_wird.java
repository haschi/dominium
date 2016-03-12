package de.therapeutenkiller.coding.Gegeben_ist_eine_öffentliche_Methode;

import de.therapeutenkiller.coding.aspekte.ArgumentIstNullException;
import org.testng.annotations.Test;

public final class Wenn_die_Methode_mit_einem_null_Parameter_aufgerufen_wird {

    @Test(expectedExceptions = ArgumentIstNullException.class)
    public void dann_wird_eine_ArgumentIstNullException_ausgelöst() {
        this.öffentlicheMethode(null);
    }

    public void öffentlicheMethode(final Object einWert) {
    }
}
