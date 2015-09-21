package de.therapeutenkiller.haushaltsbuch.domaene;

/**
 * Created by mhaschka on 20.09.15.
 */
public class Geldbörse {
    public static Geldbörse Leer = new Geldbörse(new Geld(0, "€"));
    private Geld geld;

    public Geldbörse(Geld einGeld) {
        this.geld = einGeld;
    }

    public void hineinstecken(Geld einGeld) {
        this.geld = this.geld.hinzufügen(einGeld);
    }

    public Geld getInhalt() {
        return this.geld;
    }
}
