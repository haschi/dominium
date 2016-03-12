package de.therapeutenkiller.coding.Gegeben_ist_eine_geschützte_Methode;

import org.testng.annotations.Test;

public final class Wenn_die_Methode_mit_einem_nichtnull_Parameter_aufgerufen_wird {

    @Test
    public void dann_wird_keine_Ausnahme_ausgelöst() {
        this.geschützteMethode("Hello World");
    }

    protected void geschützteMethode(Object einWert) {
    }
}
