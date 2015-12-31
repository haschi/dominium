package de.therapeutenkiller.haushaltsbuch.domaene.aggregat;

public interface Buchungsregel {
    boolean kannErtragBuchen(Buchungssatz buchungssatz);
}
