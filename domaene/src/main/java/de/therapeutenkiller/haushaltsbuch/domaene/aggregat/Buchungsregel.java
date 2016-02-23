package de.therapeutenkiller.haushaltsbuch.domaene.aggregat;

import javax.money.MonetaryAmount;

public interface Buchungsregel {
    boolean kannErtragBuchen(Buchungssatz buchungssatz);

    boolean kannVerlustBuchen(Buchungssatz buchungssatz);

    Buchungssatz buchungssatzFürAnfangsbestand(String kontoname, MonetaryAmount betrag);
}
