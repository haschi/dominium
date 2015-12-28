package de.therapeutenkiller.haushaltsbuch.domaene.aggregat;

import de.therapeutenkiller.haushaltsbuch.api.Kontoart;

/**
 * Created by matthias on 28.12.15.
 */
public class Buchungsregelfabrik {
    public static Buchungsregel erzeugen(Kontoart kontoart) {
        switch (kontoart){
            case Ertrag: return new Buchungsregel();
            default: return new Buchungsregel();
        }
    }
}
