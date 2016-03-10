package de.therapeutenkiller.coding.Gegeben_ist_eine_innere_Klasse_mit_explizitem_Konstruktor;

/**
 * Created by m.haschka on 11/5/15.
 */
public class Wenn_die_innere_Klasse_mit_null_instanziiert_wird {

    // Anm. d. R.: Innere Klassen von der der Prüfung generell ausgeschlossen.
    public void dann_wird_keine_Ausnahme_ausgelöst() {
        new InnereKlasse(null);
    }

    private class InnereKlasse {
        public InnereKlasse(Object o) {
        }
    }
}
