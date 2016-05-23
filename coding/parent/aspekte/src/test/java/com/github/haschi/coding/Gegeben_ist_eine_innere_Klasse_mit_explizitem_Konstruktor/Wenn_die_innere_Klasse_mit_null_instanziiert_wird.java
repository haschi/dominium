package com.github.haschi.coding.Gegeben_ist_eine_innere_Klasse_mit_explizitem_Konstruktor;

public class Wenn_die_innere_Klasse_mit_null_instanziiert_wird {

    // Anm. d. R.: Innere Klassen von der der Prüfung generell ausgeschlossen.
    public void dann_wird_keine_Ausnahme_ausgelöst() {
        new InnereKlasse(null);
    }

    private class InnereKlasse {
        InnereKlasse(final Object o) {
            super();
        }
    }
}
