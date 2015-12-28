package de.therapeutenkiller.haushaltsbuch.domaene.aggregat;

/**
 * Created by matthias on 28.12.15.
 */
public interface Buchungsregel {
    boolean kannAusgabeBuchen(Buchungssatz buchungssatz);
}
