package com.github.haschi.haushaltsbuch.domaene.aggregat;

import javax.money.MonetaryAmount;

public interface Buchungsregel {
    boolean kannErtragBuchen(Buchungssatz buchungssatz);

    boolean kannVerlustBuchen(Buchungssatz buchungssatz);

    Buchungssatz buchungssatzFürAnfangsbestand(Konto konto, MonetaryAmount betrag);
}
